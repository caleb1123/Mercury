package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.JewelryCategoryConverter;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryCategoryRepository;
import com.g3.Jewelry_Auction_System.service.JewelryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JewelryCategoryServiceImpl implements JewelryCategoryService {
    @Autowired
    private JewelryCategoryRepository jewelryCategoryRepository;

    @Autowired
    private JewelryCategoryConverter jewelryCategoryConverter;

    @Override
    public List<JewelryCategoryDTO> searchJewelryCategory(String input) {
        List<JewelryCategoryDTO> list = jewelryCategoryConverter.convertToDTOList(jewelryCategoryRepository.getJewelriesByCategory(input));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }
}
