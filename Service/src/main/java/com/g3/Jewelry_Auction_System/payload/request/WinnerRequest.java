package com.g3.Jewelry_Auction_System.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WinnerRequest {
    String to;
    String auctionName;
    double winningBid;
    String fullname;
}
