package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuctionConverter {
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JewelryRepository jewelryRepository;
    @Autowired
    JewelryConverter jewelryConverter;

    public Auction toEntity(AuctionDTO auctionDTO) {
        if(auctionDTO == null) return null;
        Auction auction = new Auction();
        auction.setAuctionId(auctionDTO.getAuctionId());
        auction.setStartDate(auctionDTO.getStartDate());
        auction.setEndDate(auctionDTO.getEndDate());
        auction.setCurrentPrice(auctionDTO.getCurrentPrice());
        auction.setStatus(auctionDTO.getStatus());
        auction.setJewelry(jewelryRepository.getReferenceById(auctionDTO.getJewelryId()));
        auction.setAccount(accountRepository.getReferenceById(auctionDTO.getWinnerId()));
        return auction;
    }
    public AuctionDTO toDTO(Auction auction) {
        if(auction == null) return null;
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setAuctionId(auction.getAuctionId());
        auctionDTO.setStartDate(auction.getStartDate());
        auctionDTO.setEndDate(auction.getEndDate());
        auctionDTO.setCurrentPrice(auction.getCurrentPrice());
        auctionDTO.setStatus(auction.getStatus());
        auctionDTO.setJewelryId(jewelryConverter.toDTO(auction.getJewelry()).getJewelryId());
        auctionDTO.setWinnerId(accountConverter.toDTO(auction.getAccount()).getAccountId());
        return auctionDTO;
    }
}
