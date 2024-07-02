package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.configuration.VnPayConfig;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.entity.Payment;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.request.PaymentRequest2;
import com.g3.Jewelry_Auction_System.payload.request.PaymentResquest;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.BidRepository;
import com.g3.Jewelry_Auction_System.repository.PaymentRepository;
import com.g3.Jewelry_Auction_System.service.PaymentService;
import com.g3.Jewelry_Auction_System.vnpay.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    VnPayConfig VNPayConfig;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public PaymentResquest createVnPayPayment(PaymentRequest2 paymentRequest2,HttpServletRequest request) {
        Auction auction = auctionRepository.getReferenceById(Integer.valueOf(paymentRequest2.getAuctionId()));
        if (auction == null) {
            throw new AppException(ErrorCode.AUCTION_NOT_FOUND);
        }

        Bid bid = bidRepository.getHighestBidAmount(Integer.parseInt(paymentRequest2.getAuctionId())).orElseThrow(
                ()-> new AppException(ErrorCode.BID_NOT_FOUND)
        );

        Account account = accountRepository.getReferenceById(bid.getAccount().getAccountId());
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }


        long amount = (long) bid.getBidAmount()*100;
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

        Payment payment = new Payment();
        payment.setAmount(bid.getBidAmount());
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(LocalDate.now());
        payment.setAuction(auction);
        payment.setPaymentCode(Integer.parseInt(vnpParamsMap.get("vnp_TxnRef")));
        payment.setAccount(account);
        paymentRepository.save(payment);
        return PaymentResquest.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }




}
