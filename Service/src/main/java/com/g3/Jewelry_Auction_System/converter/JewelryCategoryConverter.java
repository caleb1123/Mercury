package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.EJewelCategory;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.entity.JewelryCategory;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class JewelryCategoryConverter {
    public JewelryCategory toEntity(JewelryCategoryDTO dto) {
        if (dto == null) return null;
        JewelryCategory entity = new JewelryCategory();
        entity.setJewelryCategoryId(dto.getJewelryCategoryId());
        entity.setImage(dto.getImage());
        entity.setCategoryName(EJewelCategory.valueOf(dto.getCategoryName()));
        return entity;
    }

    public JewelryCategoryDTO toDTO(JewelryCategory entity) {
        if (entity == null) return null;
        JewelryCategoryDTO dto = new JewelryCategoryDTO();
        dto.setJewelryCategoryId(entity.getJewelryCategoryId());
        dto.setImage(entity.getImage());
        dto.setCategoryName(entity.getCategoryName().name());
        return dto;
    }

    public List<JewelryCategoryDTO> convertToDTOList(List<JewelryCategory> jewelryCategories){
        List<JewelryCategoryDTO> jewelryCategoryDTOList = new ArrayList<>();
        for (JewelryCategory jc : jewelryCategories){
            jewelryCategoryDTOList.add(toDTO(jc));
        }
        return jewelryCategoryDTOList;
    }
}
