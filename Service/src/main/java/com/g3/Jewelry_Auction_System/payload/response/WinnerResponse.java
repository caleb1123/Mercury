package com.g3.Jewelry_Auction_System.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinnerResponse {
    private int winnerId;
    private String username;

    private double bidAmount;

    private int jewelryId;
    private String jewelryName;

    private int auctionId;
}
