package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
