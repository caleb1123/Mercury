package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    BidRepository bidRepository;
    @Autowired
    BidConverter bidConverter;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public BidDTO createBid(BidDTO bidDTO) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Account account  = accountRepository.findByUserName(name).orElse(null);
        if (name.equals("anonymousUser")) {
            throw new AppException(ErrorCode.NOT_LOGGED_IN);
        } else if (account ==null ) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        bidDTO.setAccountId(account.getAccountId());
        if (bidDTO.getBidAmount() < 1) {
            throw new AppException(ErrorCode.INVALID_VALUE);
        }
        Auction auction = auctionRepository.findById(bidDTO.getAuctionId()).orElseThrow(
                () -> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.AUCTION_CLOSED);
        } else
//        if
//        (auction.getStartDate().isAfter(LocalDateTime.now())) {
//            bidDTO.setBidTime(LocalDateTime.now());
//            Bid bid = bidConverter.toEntity(bidDTO);
//            bidRepository.save(bid);
//            return bidConverter.toDTO(bid);
//        } else if (auction.getEndDate().isAfter(LocalDateTime.now()))
        {
            Bid highestBid = bidRepository
                    .getHighestBidAmount(auction.getAuctionId()).orElse(null);
            double currentPrice = auction.getCurrentPrice();
            if (highestBid != null && highestBid.getBidAmount() > currentPrice) {
                currentPrice = highestBid.getBidAmount();
            }
            if (bidDTO.getBidAmount() > currentPrice) {
                bidDTO.setBidTime(LocalDateTime.now());
                Bid bid = bidConverter.toEntity(bidDTO);
                bidRepository.save(bid);
                return bidDTO;
            } else {
                throw new AppException(ErrorCode.INVALID_BID);
            }
        }
    }
    @Override
    public void updateBid(BidDTO bidDTO, int id) {
        if (bidDTO.getBidId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Bid bid = bidRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
        if (bidDTO.getBidAmount() >= bid.getBidAmount()) {
            throw new AppException(ErrorCode.INVALID_BID);
        }
        bid.setBidAmount(bidDTO.getBidAmount());
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);
    }
    @Override
    public void deleteBid(int id) {
        Bid bid = bidRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
    }
    @Override
    public List<BidDTO> getAllBid() {
        List<Bid> bids = bidRepository.findAll();
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Bid bid : bids) {
            bidDTOList.add(bidConverter.toDTO(bid));
        }
        return bidDTOList;
    }
    @Override
    public List<BidResponse> getBidByAuction(int auctionId) {
        List<Object[]> bidData = bidRepository.getBidResponseListByAuctionId(auctionId);
        return bidData.stream().map(data -> new BidResponse(
                (Double) data[0],
                (String) data[1],
                (Timestamp) data[2]
        )).collect(Collectors.toList());
    }
    @Override
    public AccountDTO getAccountByBid(int bidId) {
        Bid bid = bidRepository
                .findById(bidId)
                .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
        Account account = bid.getAccount();
        return accountConverter.toDTO(account);
    }
}
