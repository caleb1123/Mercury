package com.g3.Jewelry_Auction_System.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResquest implements Serializable {
    String code;
    String message;
    String paymentUrl;
}
