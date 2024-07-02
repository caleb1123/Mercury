package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.EPaymentMethod;
import com.g3.Jewelry_Auction_System.payload.DTO.PaymentDTO;
import com.g3.Jewelry_Auction_System.entity.Payment;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionConverter auctionConverter;

    public Payment toEntity(PaymentDTO dto){
        if (dto==null) return null;
        Payment entity = new Payment();
        entity.setPaymentId(dto.getPaymentId());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setAmount(dto.getAmount());
        entity.setPaymentStatus(dto.getPaymentStatus());
        entity.setPaymentCode(dto.getPaymentCode());
        entity.setAuction(auctionRepository.getReferenceById(dto.getAuctionId()));
        entity.setAccount(accountRepository.getReferenceById(dto.getAccountId()));
        return entity;
    }

    public PaymentDTO toDTO(Payment entity){
        if (entity==null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(entity.getPaymentId());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setAmount(entity.getAmount());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setPaymentCode(entity.getPaymentCode());
        dto.setAuctionId(auctionConverter.toDTO(entity.getAuction()).getAuctionId());
        dto.setAccountId(accountConverter.toDTO(entity.getAccount()).getAccountId());
        return dto;
    }
}
