package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bid")
public class BidController {
    @Autowired
    BidService bidService;
    @CrossOrigin(origins = "http://localhost:3001")

    @PostMapping("/create")
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bidDTO) {
        BidDTO bid = bidService.createBid(bidDTO);
        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }
    @PutMapping("/update/{bidId}")
    public ResponseEntity<Bid> updateBid(@RequestBody BidDTO bidDTO, @PathVariable int bidId) {
        bidService.updateBid(bidDTO, bidId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list")
    public ResponseEntity<List<BidDTO>> getAllBid() {
        List<BidDTO> bids = bidService.getAllBid();
        if (bids == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bids, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{auctionId}")
    public ResponseEntity<List<BidResponse>> getBidByAuction(@PathVariable int auctionId) {
        List<BidResponse> bids = bidService.getBidByAuction(auctionId);
        if (bids == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bids, HttpStatus.OK);
        }
    }
    @GetMapping("/{bidId}/account")
    public ResponseEntity<AccountDTO> getAccountByBid(@PathVariable int bidId) {
        AccountDTO account = bidService.getAccountByBid(bidId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }
}
