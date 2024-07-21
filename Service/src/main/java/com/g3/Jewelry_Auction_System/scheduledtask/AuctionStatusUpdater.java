package com.g3.Jewelry_Auction_System.scheduledtask;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuctionStatusUpdater {

    @Autowired
    private AuctionRepository auctionRepository;

    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    public void updateAuctionStatus() {
        LocalDateTime now = LocalDateTime.now();

        // Tìm các phiên đấu giá cần cập nhật trạng thái
        List<Auction> auctionsToStart = auctionRepository.findByStartDateBeforeAndEndDateAfterAndStatus(now, now, "Pending");
        List<Auction> auctionsToEnd = auctionRepository.findByEndDateBeforeAndStatus(now, "Ongoing");

        // Cập nhật trạng thái các phiên đấu giá bắt đầu
        for (Auction auction : auctionsToStart) {
            if (now.isAfter(auction.getStartDate()) && now.isBefore(auction.getEndDate())) {
                auction.setStatus("Ongoing");
                auctionRepository.save(auction);
            }
        }

        // Cập nhật trạng thái các phiên đấu giá kết thúc
        for (Auction auction : auctionsToEnd) {
            if (now.isAfter(auction.getEndDate())) {
                auction.setStatus("Ended");
                auctionRepository.save(auction);
            }
        }
    }
}
