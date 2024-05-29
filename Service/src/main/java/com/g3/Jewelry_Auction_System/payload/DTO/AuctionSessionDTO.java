package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuctionSessionDTO {
    private int auctionSessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean status;
    private Auction auction;
    private Account account;
}
