package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;

import java.util.List;

public interface JewelryService {
    void delistJewelry(int jewelryId);
    JewelryDTO addJewelry(JewelryDTO jewelryDTO);
    JewelryDTO updateJewelry(JewelryDTO jewelryDTO, int id);
    List<JewelryDTO> getAllJewelry();
    List<JewelryDTO> searchName(String jewelryName);
    JewelryDTO getJewelryDetail(int jewelryId);
}
