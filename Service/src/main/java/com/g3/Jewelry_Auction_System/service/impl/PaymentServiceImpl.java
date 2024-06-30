package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.configuration.VnPayConfig;
import com.g3.Jewelry_Auction_System.payload.request.PaymentRequest2;
import com.g3.Jewelry_Auction_System.payload.request.PaymentResquest;
import com.g3.Jewelry_Auction_System.service.PaymentService;
import com.g3.Jewelry_Auction_System.vnpay.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    VnPayConfig VNPayConfig;

    @Override
    public PaymentResquest createVnPayPayment(PaymentRequest2 paymentRequest2,HttpServletRequest request) {
        long amount = 10000000;
        String bankCode = paymentRequest2.getBankCode();
        String auctionId = paymentRequest2.getAuctionId();
        String username = paymentRequest2.getUsername();
        String transactionId = paymentRequest2.getTransactionId();
        Map<String, String> vnpParamsMap;

        if (transactionId != null) {
            int id = Integer.parseInt(transactionId);
            vnpParamsMap = VNPayConfig.getVNPayConfig(auctionId, username, id);
        } else {
            vnpParamsMap = VNPayConfig.getVNPayConfig(auctionId, username, 0);
        }
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Build query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(VNPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = VNPayConfig.getVnpPayUrl() + "?" + queryUrl;

        return PaymentResquest.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}
