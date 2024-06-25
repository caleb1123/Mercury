package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(AuctionDTO auction);
    void updateAuction(AuctionDTO auction, int id);
    void deleteAuction(int auctionId);
    List<AuctionDTO> getAuctionList();
    List<AuctionDTO> getAuctionByStatus(boolean status);
    List<AuctionDTO> getLiveAuctionList();
    List<AuctionDTO> getUpcomingAuctionList();
    WinnerResponse getWinner(int auctionId);
    BidDTO getHighestBid(int auctionId);

}
