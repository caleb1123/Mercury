package com.g3.Jewelry_Auction_System.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionToEndResponse {
    private int auctionId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double currentPrice;
    private String status;
    private int jewelryId;
    private Integer winnerId;  // Để winnerId có thể null
    private int daysToEnd;
}
