package com.g3.Jewelry_Auction_System.service.impl;


import com.g3.Jewelry_Auction_System.converter.JewelryConverter;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;

import com.g3.Jewelry_Auction_System.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JewelryServiceImpl implements JewelryService {
    @Autowired
    JewelryRepository   jewelryRepository;
    @Autowired
    JewelryConverter    jewelryConverter;
    @Autowired
    private JewelryCategoryRepository jewelryCategoryRepository;

    @Override
    public JewelryDTO addJewelry(JewelryDTO jewelryDTO) {
        Jewelry newJewelry = jewelryConverter.toEntity(jewelryDTO);
        jewelryRepository.save(newJewelry);
        return jewelryDTO;
    }

    @Override
    public void delistJewelry(int jewelryId) {
        Jewelry jewelry = jewelryRepository
                .findByJewelryId(jewelryId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        jewelry.setStatus(false);
        jewelryRepository.save(jewelry);
    }

    @Override
    public void updateJewelry(JewelryDTO jewelryDTO, int id) {
        Optional<Jewelry> optionalJewelry = jewelryRepository.findById(id);
        if (jewelryDTO.getJewelryName().isEmpty()
                || jewelryDTO.getImage().isEmpty()
                || jewelryDTO.getDescription().isEmpty()
                || jewelryDTO.getCondition().isEmpty()
                || jewelryDTO.getEstimate() < 0
                || jewelryDTO.getStartingPrice() < 0) {
            throw new AppException(ErrorCode.EMPTY_FIELD);
        }else if (optionalJewelry.isPresent()) {
            Jewelry jewelry = optionalJewelry.get();
            jewelry.setJewelryName(jewelryDTO.getJewelryName());
            jewelry.setDesigner(jewelryDTO.getDesigner());
            jewelry.setGemstone(jewelryDTO.getGemstone());
            jewelry.setImage(jewelryDTO.getImage());
            jewelry.setDescription(jewelryDTO.getDescription());
            jewelry.setCondition(jewelryDTO.getCondition());
            jewelry.setEstimate(jewelryDTO.getEstimate());
            jewelry.setStartingPrice(jewelryDTO.getStartingPrice());
            jewelry.setStatus(jewelryDTO.getStatus());
            jewelry.setJewelryCategory(jewelryCategoryRepository.getReferenceById(jewelryDTO.getJewelryCategoryId()));
            jewelryRepository.save(jewelry);
        } else{
            throw new RuntimeException("Jewelry not found");
        }
    }

    @Override
    public List<JewelryDTO> getAllJewelry() {
        List<Jewelry> jewelryList = jewelryRepository.findAll();
        return jewelryConverter.convertToJewelryDTOList(jewelryList);
    }

    @Override
    public List<JewelryDTO> searchName(String name) {
        return jewelryConverter.convertToJewelryDTOList(jewelryRepository.getJewelriesByName(name));
    }
}

