package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;

import java.util.List;

public interface JewelryImageService {
    JewelryImageDTO addJewelryImage(JewelryImageDTO jewelryImageDTO);

    List<JewelryImageDTO> getImagesByJewelryId(int id);
}
