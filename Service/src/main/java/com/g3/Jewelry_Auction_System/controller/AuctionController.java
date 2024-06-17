package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    @Autowired
    AuctionService auctionService;
    @PostMapping("/create")
    public ResponseEntity<AuctionDTO> createAuction(@RequestBody AuctionDTO auctionDTO) {
        AuctionDTO auction = auctionService.createAuction(auctionDTO);
        return new ResponseEntity<>(auction, HttpStatus.CREATED);
    }
    @PutMapping("/delete/{auctionId}")
    public ResponseEntity<Auction> deleteAuction(@PathVariable int auctionId) {
        auctionService.deleteAuction(auctionId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{auctionId}")
    public ResponseEntity<Auction> updateAuction(@PathVariable int auctionId, @RequestBody AuctionDTO auctionDTO) {
        auctionService.updateAuction(auctionDTO, auctionId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list")
    public ResponseEntity<List<AuctionDTO>> getAuctionList() {
        List<AuctionDTO> auctionList = auctionService.getAuctionList();
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @GetMapping("/list/{status}")
    public ResponseEntity<List<AuctionDTO>> getAuctionByStatus(@PathVariable boolean status) {
        List<AuctionDTO> auctionList = auctionService.getAuctionByStatus(status);
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @GetMapping("/list/live")
    public ResponseEntity<List<AuctionDTO>> getLiveAuctionList() {
        List<AuctionDTO> auctionList = auctionService.getLiveAuctionList();
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @GetMapping("/list/byDate")
    public ResponseEntity<List<AuctionDTO>> getAuctionByDate(@RequestParam LocalDateTime date1, LocalDateTime date2) {
        List<AuctionDTO> auctionList = auctionService.getAuctionByDate(date1, date2);
        if (auctionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(auctionList, HttpStatus.OK);
        }
    }
    @GetMapping("/{auctionId}/highestBid")
    public ResponseEntity<BidDTO> getHighestBid(@PathVariable int auctionId) {
        BidDTO bid = auctionService.getHighestBid(auctionId);
        return ResponseEntity.ok(bid);
    }
    @GetMapping("/{auctionId}/winner")
    public ResponseEntity<WinnerResponse> getWinner(@PathVariable int auctionId) {
        WinnerResponse winner = auctionService.getWinner(auctionId);
        return ResponseEntity.ok(winner);
    }


}
