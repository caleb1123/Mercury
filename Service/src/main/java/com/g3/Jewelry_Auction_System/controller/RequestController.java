package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO dto) {
        RequestDTO createdRequest = requestService.createRequest(dto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }
}
