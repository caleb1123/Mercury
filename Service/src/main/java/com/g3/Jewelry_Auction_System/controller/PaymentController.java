package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Payment;
import com.g3.Jewelry_Auction_System.payload.DTO.PaymentDTO;
import com.g3.Jewelry_Auction_System.payload.request.PaymentRequest2;
import com.g3.Jewelry_Auction_System.payload.request.PaymentResquest;
import com.g3.Jewelry_Auction_System.payload.response.PaymentResponse;
import com.g3.Jewelry_Auction_System.repository.PaymentRepository;
import com.g3.Jewelry_Auction_System.service.PaymentService;
import com.g3.Jewelry_Auction_System.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;


    @PostMapping("/vn-pay")
    public ResponseObject<PaymentResquest> pay(@RequestBody PaymentRequest2 request, HttpServletRequest httpServletRequest) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, httpServletRequest));
    }

    @GetMapping("/return")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "http://localhost:3001/PaymentSuccess";
        String urlFail = "http://localhost:3001/PaymentFailed";
        PaymentResponse payment = paymentService.handleCallback(request);
        if (payment.getCode().equals("00")) {
            response.sendRedirect(url);
        } else {
            response.sendRedirect(urlFail);
        }
    }
}