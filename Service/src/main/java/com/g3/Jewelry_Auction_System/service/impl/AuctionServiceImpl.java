package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.entity.Auction;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionConverter auctionConverter;
    @Autowired
    JewelryRepository JewelryRepository;
    @Autowired
    private JewelryRepository jewelryRepository;

    @Override
    public AuctionDTO createAuction(AuctionDTO auctionDTO) {
        LocalDateTime startDate = auctionDTO.getStartDate();
        LocalDateTime endDate = auctionDTO.getEndDate();
        Optional<Auction> existingAuction = auctionRepository
                .findByJewelry(jewelryRepository.getReferenceById(auctionDTO.getJewelryId()));
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new IllegalArgumentException("End date cannot be before current date");
        }
        if (existingAuction.isPresent() && existingAuction.get().getEndDate().isBefore(startDate)) {
            throw new IllegalArgumentException("Auction for this jewelry has not ended yet");
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
            throw new RuntimeException("Auction ID does not match request");
        }
        Auction auction = auctionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        LocalDateTime startDate = auctionDTO.getStartDate();
        LocalDateTime endDate = auctionDTO.getEndDate();
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new IllegalArgumentException("End date cannot be before current date");
        }
        auction.setStartDate(auctionDTO.getStartDate());
        auction.setEndDate(auctionDTO.getEndDate());
        auction.setCurrentPrice(auctionDTO.getCurrentPrice());
        auctionRepository.save(auction);
    }
}
