package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    BidRepository bidRepository;
    @Autowired
    BidConverter bidConverter;
    @Override
    public BidDTO createBid(BidDTO bidDTO) {
        if (bidRepository.findById(bidDTO.getBidId()).isPresent()) {
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        if (bidDTO.getBidAmount() < 1) {
            throw new IllegalArgumentException("Bid amount must be greater than 0");
        }
        Bid bid = bidConverter.toEntity(bidDTO);
        bid.setBidTime(LocalDateTime.now());
        bid = bidRepository.save(bid);
        return bidConverter.toDTO(bid);
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
            throw new RuntimeException("New bid must be greater than previous bid");
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
}
