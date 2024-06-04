package com.g3.Jewelry_Auction_System.service.impl;


import com.g3.Jewelry_Auction_System.converter.JewelryConverter;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;

import com.g3.Jewelry_Auction_System.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JewelryServiceImpl implements JewelryService {
    @Autowired
    JewelryRepository   jewelryRepository;
    @Autowired
    JewelryConverter    jewelryConverter;

    @Override
    public Jewelry addJewelry(JewelryDTO jewelryDTO) {
        Jewelry newJewelry = jewelryConverter.toEntity(jewelryDTO);
        jewelryRepository.save(newJewelry);
        return newJewelry;
    }

    @Override
    public void delistJewelry(int jewelryId) {
        Jewelry jewelry = jewelryRepository
                .findByJewelryId(jewelryId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        jewelry.setStatus(false);
        jewelryRepository.save(jewelry);
    }
}
