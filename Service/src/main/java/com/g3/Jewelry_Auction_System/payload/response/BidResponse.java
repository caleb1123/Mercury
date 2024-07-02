package com.g3.Jewelry_Auction_System.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidResponse {
    double bidAmount;
    String username;
    Timestamp bidTime;
}
