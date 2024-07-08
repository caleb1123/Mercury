package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JewelryImageConverter {
    @Autowired
    JewelryRepository jewelryRepository;
    public JewelryImage toEntity(JewelryImageDTO jewelryImageDTO) {
        if (jewelryImageDTO == null) return null;
        JewelryImage entity = new JewelryImage();
        entity.setJewelryImageId(jewelryImageDTO.getJewelryImageId());
        entity.setJewelryImageURL(jewelryImageDTO.getJewelryImageURL());
        entity.setJewelry(jewelryRepository.getReferenceById(jewelryImageDTO.getJewelryId()));
        entity.setStatus(jewelryImageDTO.isStatus());
        entity.setFileId(jewelryImageDTO.getFileId());
        return entity;
    }
    public JewelryImageDTO toDTO(JewelryImage jewelryImage) {
        if (jewelryImage == null) return null;
        JewelryImageDTO dto = new JewelryImageDTO();
        dto.setJewelryImageId(jewelryImage.getJewelryImageId());
        dto.setJewelryImageURL(jewelryImage.getJewelryImageURL());
        dto.setJewelryId(jewelryImage.getJewelry().getJewelryId());
        dto.setStatus(jewelryImage.isStatus());
        dto.setFileId(jewelryImage.getFileId());
        return dto;
    }
}
