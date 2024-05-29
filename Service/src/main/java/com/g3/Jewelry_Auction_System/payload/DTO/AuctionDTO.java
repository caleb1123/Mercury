package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuctionDTO {
    private int auctionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double currentPrice;
    private Boolean status;
    private Jewelry jewelry;
    private Account account;
    private Collection<Bid> bids;
    private Collection<Payment> payments;
}
