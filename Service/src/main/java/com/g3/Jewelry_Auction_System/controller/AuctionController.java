package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.AuctionToEndResponse;
import com.g3.Jewelry_Auction_System.payload.response.UpcomingAuctionResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    AuctionService auctionService;
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/create")
    public ResponseEntity<AuctionDTO> createAuction(@RequestBody AuctionDTO auctionDTO) {
        AuctionDTO auction = auctionService.createAuction(auctionDTO);
        return new ResponseEntity<>(auction, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/delete/{auctionId}")
    public ResponseEntity<Auction> deleteAuction(@PathVariable int auctionId) {
        auctionService.deleteAuction(auctionId);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/{auctionId}")
    public ResponseEntity<Auction> updateAuction(@PathVariable int auctionId, @RequestBody AuctionDTO auctionDTO) {
        auctionService.updateAuction(auctionDTO, auctionId);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list")
    public ResponseEntity<List<AuctionDTO>> getAuctionList() {
        List<AuctionDTO> auctionList = auctionService.getAuctionList();
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{status}")
    public ResponseEntity<List<AuctionDTO>> getAuctionByStatus(@PathVariable String status) {
        List<AuctionDTO> auctionList = auctionService.getAuctionByStatus(status);
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/live")
    public ResponseEntity<List<AuctionDTO>> getLiveAuctionList() {
        List<AuctionDTO> auctionList = auctionService.getLiveAuctionList();
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/upcoming")
    public ResponseEntity<List<UpcomingAuctionResponse>> getUpcomingAuctionList() {
        List<UpcomingAuctionResponse> auctionList = auctionService.getUpcomingAuctionList();
        return new ResponseEntity<>(auctionList, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{auctionId}/highestBid")
    public ResponseEntity<BidDTO> getHighestBid(@PathVariable int auctionId) {
        BidDTO bid = auctionService.getHighestBid(auctionId);
        return ResponseEntity.ok(bid);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{auctionId}/winner")
    public ResponseEntity<WinnerResponse> getWinner(@PathVariable int auctionId) {
        WinnerResponse winner = auctionService.getWinner(auctionId);
        return ResponseEntity.ok(winner);
    }

    @GetMapping("/days-to-end")
    public List<AuctionToEndResponse> getAuctionsWithDaysToEnd() {
        return auctionService.getAuctionsWithDaysToEnd();
    }
    @PostMapping("/{auctionId}/sendWinnerEmail")
    public ResponseEntity<Void> sendEmailToWinner(@PathVariable int auctionId) {
        auctionService.sendEmailToWinner(auctionId);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{auctionId}/targetDate")
    public ResponseEntity<String> getTargetDate(@PathVariable int auctionId) {
        LocalDateTime targetDate = auctionService.getTargetDate(auctionId);
        String formattedDate = targetDate.format(DateTimeFormatter.ISO_DATE_TIME);
        return ResponseEntity.ok(formattedDate);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/thisUser/wonAuctions")
    public ResponseEntity<List<AuctionDTO>> getWonAuctions() {
        List<AuctionDTO> auctionList = auctionService.getWonAuctions();
        return new ResponseEntity<>(auctionList, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/{auctionId}/stop")
    public ResponseEntity<String> stopAuction(@PathVariable int auctionId) {
        try {
            auctionService.stopAuction(auctionId);
            return ResponseEntity.ok("Auction stopped successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
}
}
