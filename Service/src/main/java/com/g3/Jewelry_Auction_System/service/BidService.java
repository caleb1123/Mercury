package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;

import java.util.List;

public interface BidService {
    BidDTO createBid(BidDTO bidDTO);
    void updateBid(BidDTO bidDTO, int id);
    void deleteBid(int id);
    List<BidDTO> getAllBid();
}
