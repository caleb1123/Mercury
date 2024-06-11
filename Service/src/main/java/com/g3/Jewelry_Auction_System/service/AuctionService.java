package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;

public interface AuctionService {
    AuctionDTO createAuction(AuctionDTO auction);
    void updateAuction(AuctionDTO auction, int id);
    void deleteAuction(int auctionId);
}
