package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
import com.g3.Jewelry_Auction_System.payload.response.IncrementResponse;
import com.g3.Jewelry_Auction_System.service.AccountService;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/bid")
public class BidController {
    @Autowired
    BidService bidService;
    @Autowired
    AccountService accountService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/create")
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bidDTO) {
        BidDTO bid = bidService.createBid(bidDTO);

        // Send bid response to WebSocket topic
        AccountDTO account = accountService.getAccountByAccountId(bid.getAccountId());
        BidResponse bidResponse = BidResponse.builder()
                .bidAmount(bid.getBidAmount())
                .username(account.getUserName())
                .bidTime(Timestamp.valueOf(bid.getBidTime()))
                .build();
        messagingTemplate.convertAndSend("/topic/bid/" + bid.getAuctionId(), bidResponse);

        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/{bidId}")
    public ResponseEntity<Bid> updateBid(@RequestBody BidDTO bidDTO, @PathVariable int bidId) {
        bidService.updateBid(bidDTO, bidId);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3001")
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

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{bidId}/account")
    public ResponseEntity<AccountDTO> getAccountByBid(@PathVariable int bidId) {
        AccountDTO account = bidService.getAccountByBid(bidId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @PostMapping("/submitBid")
    public ResponseEntity<BidResponse> submitBid(@RequestBody BidDTO bidDTO) {
        BidDTO newBid = bidService.createBid(bidDTO);
        AccountDTO account = accountService.getAccountByAccountId(bidDTO.getBidId());
        BidResponse bidResponse = BidResponse.builder()
                .bidAmount(newBid.getBidAmount())
                .username(account.getUserName()) // Assuming Account has a getUsername method
                .bidTime(Timestamp.valueOf(newBid.getBidTime()))
                .build();

        // Send bid response to WebSocket topic
        messagingTemplate.convertAndSend("/topic/bids/" + newBid.getAuctionId(), bidResponse);

        // Return the created bid response DTO to the client
        return new ResponseEntity<>(bidResponse, HttpStatus.CREATED);
    }



//    @CrossOrigin(origins = "http://localhost:3001")
//    @PostMapping("/create")
//    public ResponseEntity<BidResponse> submitBid(@RequestBody BidDTO bidDTO) {
//         BidDTO newBid = bidService.createBid(bidDTO);
//        AccountDTO account = accountService.getAccountByAccountId(bidDTO.getBidId());
//        BidResponse bidResponse = BidResponse.builder()
//                .bidAmount(newBid.getBidAmount())
//                .username(account.getUserName()) // Assuming Account has a getUsername method
//                .bidTime(Timestamp.valueOf(newBid.getBidTime()))
//                .build();
//
//        // Send bid response to WebSocket topic
//        messagingTemplate.convertAndSend("/topic/bids/" + newBid.getAuctionId(), bidResponse);
//
//        // Return the created bid response DTO to the client
//        return new ResponseEntity<>(bidResponse, HttpStatus.CREATED);
//    }

    @GetMapping("/increment/{auctionId}")
    public ResponseEntity<List<IncrementResponse>> getIncrementList(@PathVariable int auctionId) {
        List<IncrementResponse> incrementResponses = bidService.incrementList(auctionId);
        return new ResponseEntity<>(incrementResponses, HttpStatus.OK);
    }
}

