package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
import com.g3.Jewelry_Auction_System.payload.response.IncrementResponse;

import java.util.List;

public interface BidService {
    BidDTO createBid(BidDTO bidDTO);
    void updateBid(BidDTO bidDTO, int id);
    void deleteBid(int id);
    List<BidDTO> getAllBid();
    List<BidResponse> getBidByAuction(int auctionId);
    AccountDTO getAccountByBid(int bidId);
    List<IncrementResponse> incrementList(int AuctionId);
}
