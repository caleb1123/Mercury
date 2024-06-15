package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.entity.Auction;

import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        LocalDateTime startDate = auctionDTO.getStartDate();
        LocalDateTime endDate = auctionDTO.getEndDate();
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new IllegalArgumentException("End date cannot be before current date");
        }
        if (auctionDTO.getCurrentPrice() < 1) {
            throw new IllegalArgumentException("Current price cannot be less than 1");
        }
        if (auctionDTO.getStartDate() != null) {
            auction.setStartDate(auctionDTO.getStartDate());
        }
        if (auctionDTO.getEndDate() != null) {
            auction.setEndDate(auctionDTO.getEndDate());
        }
        if (auctionDTO.getCurrentPrice() != auction.getCurrentPrice()) {
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
    public List<AuctionDTO> getAuctionByDate(LocalDateTime date1, LocalDateTime date2) {
        List<AuctionDTO> auctionDTOList = getAuctionList();
        if (date1 != null && date2 == null) {
            auctionDTOList.removeIf(auctionDTO
                    -> date1.isBefore(auctionDTO.getStartDate()) || date1.isAfter(auctionDTO.getEndDate()));
        }
        if (date1 != null && date2 != null) {
            LocalDateTime startDate = date1.isBefore(date2) ? date1 : date2;
            LocalDateTime endDate = date1.isAfter(date2) ? date1 : date2;
            auctionDTOList.removeIf(auctionDTO ->
                    startDate.isAfter(auctionDTO.getEndDate()) || endDate.isBefore(auctionDTO.getStartDate())
            );
        }
        return auctionDTOList;
    }
    @Override
    public WinnerResponse getWinner(int auctionId) {
        return null;
    }
}
