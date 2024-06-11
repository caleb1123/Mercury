package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Request;
import com.g3.Jewelry_Auction_System.payload.DTO.RequestDTO;
import com.g3.Jewelry_Auction_System.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/preliminary/{requestId}")
    public ResponseEntity<Request> updatePreliminaryPrice(@PathVariable int requestId, @RequestBody RequestDTO dto) {
        requestService.updatePreliminaryPrice(requestId, dto);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/final/{requestId}")
    public ResponseEntity<Request> updateFinalPrice(@PathVariable int requestId, @RequestBody RequestDTO dto) {
        requestService.updateFinalPrice(requestId, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list")
    public ResponseEntity<List<RequestDTO>> getRequestList() {
        List<RequestDTO> requestList = requestService.getRequestList();
        if (requestList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        }
    }
    @PutMapping("/delete/{requestId}")
    public ResponseEntity<Request> deactivateAccount(@PathVariable int requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.ok().build(); // Return 200 OK on successful deactivation
    }
}
