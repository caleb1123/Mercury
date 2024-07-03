package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WinnerResponse {
    private int winnerId;
    private String username;

    private double bidAmount;

    private int jewelryId;
    private String jewelryName;

    private int auctionId;
}

