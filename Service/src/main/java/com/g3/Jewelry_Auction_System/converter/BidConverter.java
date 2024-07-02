package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.BidDTO;
import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BidConverter {
    @Autowired
    AuctionConverter auctionConverter;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    public Bid toEntity(BidDTO bidDTO) {
        if (bidDTO == null) return null;
        Bid bid = new Bid();
        bid.setBidId(bidDTO.getBidId());
        bid.setBidAmount(bidDTO.getBidAmount());
        bid.setBidTime(bidDTO.getBidTime());
        bid.setAuction(auctionRepository.getReferenceById(bidDTO.getAuctionId()));
        bid.setAccount(accountRepository.getReferenceById(bidDTO.getAccountId()));
        return bid;
    }

    public BidDTO toDTO(Bid bid) {
        if (bid == null) return null;
        BidDTO dto = new BidDTO();
        dto.setBidId(bid.getBidId());
        dto.setBidAmount(bid.getBidAmount());
        dto.setBidTime(bid.getBidTime());
        dto.setAuctionId(auctionConverter.toDTO(bid.getAuction()).getAuctionId());
        dto.setAccountId(accountConverter.toDTO(bid.getAccount()).getAccountId());
        return dto;
    }

    public List<BidDTO> toDTOList(List<Bid> bids){
        List<BidDTO> dtoList = new ArrayList<>();
        for (Bid bid : bids){
            dtoList.add(toDTO(bid));
        }
        return dtoList;
    }
}
