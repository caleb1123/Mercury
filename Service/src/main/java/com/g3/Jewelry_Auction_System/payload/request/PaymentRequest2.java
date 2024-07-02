package com.g3.Jewelry_Auction_System.payload.request;

import lombok.Data;


import lombok.Builder;

@Data
@Builder
public class PaymentRequest2 {
    private long amount;
    private String bankCode;
    private String auctionId;
    private String username;
    private String transactionId;
    private String code;
    private String message;
    private String paymentUrl;
}

