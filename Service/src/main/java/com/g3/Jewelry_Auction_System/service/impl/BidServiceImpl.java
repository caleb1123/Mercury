package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.BidConverter;
import com.g3.Jewelry_Auction_System.entity.Bid;
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
        Bid bid = bidConverter.toEntity(bidDTO);
        bid.setBidTime(LocalDateTime.now());
        bid = bidRepository.save(bid);
        return bidConverter.toDTO(bid);
    }
    @Override
    public void updateBid(BidDTO bidDTO, int id) {
        if (bidDTO.getBidId() != id) {
            throw new RuntimeException("Bid id does not match request");
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
