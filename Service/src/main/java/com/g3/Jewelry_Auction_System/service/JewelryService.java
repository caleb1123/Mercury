package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;

public interface JewelryService {
    void delistJewelry(int jewelryId);
    Jewelry addJewelry(JewelryDTO jewelryDTO);
}
