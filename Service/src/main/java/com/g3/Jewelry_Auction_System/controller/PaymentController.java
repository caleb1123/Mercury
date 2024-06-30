package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.request.PaymentRequest2;
import com.g3.Jewelry_Auction_System.payload.request.PaymentResquest;
import com.g3.Jewelry_Auction_System.service.PaymentService;
import com.g3.Jewelry_Auction_System.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;


    @PostMapping("/vn-pay")
    public ResponseObject<PaymentResquest> pay(@RequestBody PaymentRequest2 request, HttpServletRequest httpServletRequest) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, httpServletRequest));
    }
}
