package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.request.JewelryPageRequest;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.JewelryCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JewelryConverter {
    @Autowired
    JewelryCategoryConverter jewelryCategoryConverter;
    @Autowired
    JewelryCategoryRepository JewelryCategoryRepository;
    @Autowired
    private JewelryCategoryRepository jewelryCategoryRepository;

    public Jewelry toEntity(JewelryDTO dto){
        if (dto==null) return null;
        Jewelry entity = new Jewelry();
        entity.setJewelryId(dto.getJewelryId());
        entity.setJewelryCode(dto.getJewelryCode());
        entity.setJewelryName(dto.getJewelryName());
        entity.setDesigner(dto.getDesigner());
        entity.setGemstone(dto.getGemstone());

        entity.setDescription(dto.getDescription());
        entity.setStartingPrice(dto.getStartingPrice());
        entity.setStatus(dto.getStatus());
        entity.setCondition(dto.getCondition());
        entity.setEstimate(dto.getEstimate());
        entity.setJewelryCategory(jewelryCategoryRepository.getReferenceById(dto.getJewelryCategoryId()));
        return entity;
    }

    public JewelryDTO toDTO(Jewelry entity){
        if (entity==null) return null;
        JewelryDTO dto = new JewelryDTO();
        dto.setJewelryId(entity.getJewelryId());
        dto.setJewelryCode(entity.getJewelryCode());
        dto.setJewelryName(entity.getJewelryName());
        dto.setDesigner(entity.getDesigner());
        dto.setGemstone(entity.getGemstone());

        dto.setDescription(entity.getDescription());
        dto.setStartingPrice(entity.getStartingPrice());
        dto.setStatus(entity.getStatus());
        dto.setCondition(entity.getCondition());
        dto.setEstimate(entity.getEstimate());
        dto.setJewelryCategoryId(jewelryCategoryConverter.toDTO(entity.getJewelryCategory()).getJewelryCategoryId());
        return dto;
    }

    public List<JewelryDTO> convertToJewelryDTOList(List<Jewelry> jewelries){
        List<JewelryDTO> jewelryDTOList = new ArrayList<>();
        for (Jewelry j : jewelries){
            jewelryDTOList.add(toDTO(j));
        }
        return jewelryDTOList;
    }
}
