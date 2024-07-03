package com.g3.Jewelry_Auction_System.payload.request;

import lombok.Data;


import lombok.Builder;

@Data
@Builder
public class PaymentRequest2 {
    private String bankCode;
    private int auctionId;
    private String username;
    private String transactionId;
    private String code;
    private String message;
    private String paymentUrl;
}

