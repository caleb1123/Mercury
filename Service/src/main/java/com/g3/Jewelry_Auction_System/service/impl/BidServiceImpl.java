package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    BidRepository bidRepository;
    @Autowired
    BidConverter bidConverter;
    @Autowired
    AuctionRepository auctionRepository;

    @Override
    public BidDTO createBid(BidDTO bidDTO) {
        if (bidRepository.findById(bidDTO.getBidId()).isPresent()) {
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        if (bidDTO.getBidAmount() < 0) {
            throw new IllegalArgumentException("Bid amount must be greater than 0");
        }
        Auction auction = auctionRepository.findById(bidDTO.getAuctionId()).orElseThrow(
                () -> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.AUCTION_CLOSED);
        }
        if (auction.getStartDate().isAfter(LocalDateTime.now())) {
            bidDTO.setBidTime(LocalDateTime.now());
            Bid bid = bidConverter.toEntity(bidDTO);
            bidRepository.save(bid);
            return bidDTO;
        } else if (auction.getEndDate().isAfter(LocalDateTime.now()))
        {
            Optional<Bid> highestBid = bidRepository
                    .getHighestBidAmount(auction.getAuctionId());
            double currentPrice = auction.getCurrentPrice();
            if (highestBid.isPresent() && highestBid.get().getBidAmount() > currentPrice) {
                currentPrice = highestBid.get().getBidAmount();
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
        throw new AppException(ErrorCode.INVALID_DOB);
    }
    @Override
    public void updateBid(BidDTO bidDTO, int id) {
        if (bidDTO.getBidId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Bid bid = bidRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Bid not found"));
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
                .orElseThrow(() -> new RuntimeException("Bid not found"));

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
    public List<BidDTO> getBidByAuction(int auctionId) {
        List<Bid> bidList = bidRepository.findByAuctionId(auctionId);
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Bid bid : bidList) {
            bidDTOList.add(bidConverter.toDTO(bid));
        }
        return bidDTOList;
    }
}
