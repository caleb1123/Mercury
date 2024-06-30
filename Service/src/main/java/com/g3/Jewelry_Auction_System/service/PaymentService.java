package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.request.PaymentRequest2;
import com.g3.Jewelry_Auction_System.payload.request.PaymentResquest;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResquest createVnPayPayment(PaymentRequest2 paymentRequest2,HttpServletRequest request);
}
