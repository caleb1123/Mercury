package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;

import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionConverter auctionConverter;
    @Autowired
    JewelryRepository JewelryRepository;
    @Autowired
    JewelryRepository jewelryRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    BidConverter bidConverter;
    @Autowired
    BidRepository bidRepository;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        if (auctionRepository.findById(auctionDTO.getAuctionId()).isPresent()) {
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        LocalDateTime startDate = auctionDTO.getStartDate();
        LocalDateTime endDate = auctionDTO.getEndDate();
        List<Auction> existingAuctions = auctionRepository
                .findByJewelry(jewelryRepository.getReferenceById(auctionDTO.getJewelryId()));
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new IllegalArgumentException("End date cannot be before current date");
        }
        if (auctionDTO.getCurrentPrice() < 1) {
            throw new IllegalArgumentException("Current price cannot be less than 1");
        }
        if (existingAuctions.stream().anyMatch(Auction::getStatus)) {
            throw new IllegalArgumentException("An auction for this jewelry is still active");
        }
        Auction auction = auctionConverter.toEntity(auctionDTO);
        auctionRepository.save(auction);
        return auctionConverter.toDTO(auction);
    }
    @Override
    public void deleteAuction(int auctionId) {
        Auction auction = auctionRepository
                .findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.setStatus(false);
        auctionRepository.save(auction);
    }
    @Override
    public void updateAuction(AuctionDTO auctionDTO, int id) {
        if (auctionDTO.getAuctionId() != id) {
            throw new AppException(ErrorCode.ID_NOT_MATCHED);
        }
        Auction auction = auctionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auctionDTO.getStartDate() != null) {
            if (auctionDTO.getStartDate().isAfter(auctionDTO.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            } else {
                auction.setStartDate(auctionDTO.getStartDate());
            }
        }
        if (auctionDTO.getEndDate() != null) {
            if (LocalDateTime.now().isAfter(auctionDTO.getEndDate())) {
                throw new IllegalArgumentException("End date cannot be before current date");
            } else {
                auction.setEndDate(auctionDTO.getEndDate());
            }
        }
        if (auctionDTO.getCurrentPrice() > auction.getCurrentPrice()) {
            auction.setCurrentPrice(auctionDTO.getCurrentPrice());
        }
        if (auctionDTO.getStatus() != null) {
            auction.setStatus(auctionDTO.getStatus());
        }
        auctionRepository.save(auction);
    }
    @Override
    public List<AuctionDTO> getAuctionList() {
        List<Auction> auctions = auctionRepository.findAll();
        List<AuctionDTO> auctionDTOList = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionDTOList.add(auctionConverter.toDTO(auction));
        }
        return auctionDTOList;
    }
    @Override
    public List<AuctionDTO> getAuctionByStatus(boolean status) {
        List<AuctionDTO> auctionDTOList = getAuctionList();
        auctionDTOList.removeIf(auctionDTO -> !auctionDTO.getStatus().equals(status));
        return auctionDTOList;
    }
    @Override
    public List<AuctionDTO> getLiveAuctionList() {
        List<AuctionDTO> auctionDTOList = getAuctionList();
        LocalDateTime now = LocalDateTime.now();
        auctionDTOList.removeIf(auctionDTO
                -> now.isBefore(auctionDTO.getStartDate()) || now.isAfter(auctionDTO.getEndDate()));
        return auctionDTOList;
    }
    @Override
    public List<AuctionDTO> getUpcomingAuctionList() {
        List<AuctionDTO> allAuctions = getAuctionList();
        LocalDateTime now = LocalDateTime.now();

        List<AuctionDTO> liveAuctions = getLiveAuctionList();

        List<AuctionDTO> upcomingAuctions = allAuctions.stream()
                .filter(auction -> now.isBefore(auction.getStartDate()))
                .sorted(Comparator.comparing(AuctionDTO::getStartDate))
                .toList();

        // Combine live auctions and upcoming auctions
        liveAuctions.addAll(upcomingAuctions);

        return liveAuctions;
    }
    @Override
    public BidDTO getHighestBid(int auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
                ()-> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        Bid highestBid = bidRepository
                .getHighestBidAmount(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.BID_NOT_FOUND));
        return bidConverter.toDTO(highestBid);
    }
    @Override
    public WinnerResponse getWinner(int auctionId) {
        WinnerResponse winner = new WinnerResponse();
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(
                ()-> new AppException(ErrorCode.AUCTION_NOT_FOUND)
        );
        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            Jewelry jewelry = auction.getJewelry();
            Bid bid = bidRepository.getHighestBidAmount(auctionId).orElseThrow(
                            () -> new AppException(ErrorCode.BID_NOT_FOUND)
                    );
            Account account = bid.getAccount();
            winner.setWinnerId(account.getAccountId());
            winner.setUsername(account.getUserName());
            winner.setBidAmount(bid.getBidAmount());
            winner.setJewelryId(jewelry.getJewelryId());
            winner.setJewelryName(jewelry.getJewelryName());
            winner.setAuctionId(auction.getAuctionId());
            return winner;
        } else {
            throw new AppException(ErrorCode.AUCTION_NOT_CLOSED);
        }
    }
}
