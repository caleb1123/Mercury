package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.stereotype.Component;

@Component
public class JewelryConverter {
    public Jewelry toEntity(JewelryDTO dto){
        if (dto==null) return null;
        Jewelry entity = new Jewelry();
        entity.setJewelryId(dto.getJewelryId());
        entity.setJewelryName(dto.getJewelryName());
        entity.setDesigner(dto.getDesigner());
        entity.setGemstone(dto.getGemstone());
        entity.setImage(dto.getImage());
        entity.setDescription(dto.getDescription());
        entity.setStartingPrice(dto.getStartingPrice());
        entity.setStatus(dto.getStatus());
        entity.setCondition(dto.getCondition());
        entity.setEstimate(dto.getEstimate());
        entity.setJewelryId(dto.getJewelryId());
        return entity;
    }

    public JewelryDTO toDTO(Jewelry entity){
        if (entity==null) return null;

        JewelryDTO dto = new JewelryDTO();
        dto.setJewelryId(entity.getJewelryId());
        dto.setJewelryName(entity.getJewelryName());
        dto.setDesigner(entity.getDesigner());
        dto.setGemstone(entity.getGemstone());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        dto.setStartingPrice(entity.getStartingPrice());
        dto.setStatus(entity.getStatus());
        dto.setCondition(entity.getCondition());
        dto.setEstimate(entity.getEstimate());
        dto.setJewelryId(entity.getJewelryId());
        return dto;
    }
}
