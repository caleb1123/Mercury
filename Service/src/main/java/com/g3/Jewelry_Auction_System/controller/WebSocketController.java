package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
import com.g3.Jewelry_Auction_System.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    BidService bidService;


    @MessageMapping("/bid//list/{auctionId}")
    @SendTo("/topic/bid/{auctionId}")
    public List<BidResponse> getBidByAuction(int auctionId){
        return bidService.getBidByAuction(auctionId);
    }
}
