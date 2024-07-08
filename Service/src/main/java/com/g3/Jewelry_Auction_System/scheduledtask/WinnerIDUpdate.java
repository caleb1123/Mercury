package com.g3.Jewelry_Auction_System.scheduledtask;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class WinnerIDUpdate {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Scheduled(fixedRate = 60000) // 1 minute
    public void updateWinners() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> auctions = auctionRepository.findByEndDateBeforeAndWinnerIdIsNull(now);

        for (Auction auction : auctions) {
            Integer highestBidderId = bidRepository.findHighestBidderId(auction.getAuctionId());
            if (highestBidderId != null) {
                auction.setWinnerId(highestBidderId);
                auctionRepository.save(auction);
            }
        }
    }
}
