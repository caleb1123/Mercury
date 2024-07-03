package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private int paymentId;
    private LocalDate paymentDate;
    private String paymentStatus;
    private double amount;
    private int paymentCode;
    private int auctionId;
    private int accountId;
}
