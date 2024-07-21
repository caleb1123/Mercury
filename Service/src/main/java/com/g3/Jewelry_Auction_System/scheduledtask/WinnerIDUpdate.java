package com.g3.Jewelry_Auction_System.scheduledtask;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WinnerIDUpdate {
    @Autowired
    BidRepository bidRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionService auctionService;
    @Scheduled(fixedRate = 60000) // 1 minute
    public void updateWinners() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> auctions = auctionRepository.findByEndDateBeforeAndWinnerIdIsNull(now);

        for (Auction auction : auctions) {
            Integer highestBidderId = bidRepository.findHighestBidderId(auction.getAuctionId());
            if (highestBidderId != null) {
                auction.setWinnerId(highestBidderId);
                auctionRepository.save(auction);
                auctionService.sendEmailToWinner(auction.getAuctionId());
            }
        }
    }
}
