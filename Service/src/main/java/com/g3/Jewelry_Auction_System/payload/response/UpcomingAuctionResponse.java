package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpcomingAuctionResponse {
    private int auctionId;
    private double currentPrice;
    private Timestamp endDate;
    private Timestamp startDate;
    private String status;
    private int jewelryId;
}
