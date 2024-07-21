package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.UpcomingAuctionResponse;
import com.g3.Jewelry_Auction_System.payload.response.AuctionToEndResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(AuctionDTO auction);
    void updateAuction(AuctionDTO auction, int id);
    void deleteAuction(int auctionId);
    List<AuctionDTO> getAuctionList();
    List<AuctionDTO> getAuctionByStatus(String status);
    List<AuctionDTO> getLiveAuctionList();
    List<UpcomingAuctionResponse> getUpcomingAuctionList();

    List<AuctionToEndResponse> getAuctionsWithDaysToEnd();

    WinnerResponse getWinner(int auctionId);
    BidDTO getHighestBid(int auctionId);

    void sendEmailToWinner(int auctionId);
    LocalDateTime getTargetDate(int auctionId);
    List<AuctionDTO> getWonAuctions();
    void stopAuction(int auctionId);
}
