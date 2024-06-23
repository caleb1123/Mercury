package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JewelryService {
    void delistJewelry(int jewelryId);
    JewelryDTO addJewelry(JewelryDTO jewelryDTO);
    JewelryDTO updateJewelry(JewelryDTO jewelryDTO, int id);
    Page<JewelryDTO> getAllJewelry(int id);
    List<JewelryDTO> searchName(String jewelryName);
    JewelryDTO getJewelryDetail(int jewelryId);
    AuctionDTO getAuctionByJewelry(int jewelryId);
}
