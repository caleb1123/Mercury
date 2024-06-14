package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.EJewelCategory;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.entity.JewelryCategory;
import org.springframework.stereotype.Component;

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
}
