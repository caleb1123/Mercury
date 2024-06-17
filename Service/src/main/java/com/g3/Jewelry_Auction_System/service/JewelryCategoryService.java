package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryCategoryDTO;

import java.util.List;

public interface JewelryCategoryService {
    List<JewelryCategoryDTO> searchJewelryCategory(String input);
}
