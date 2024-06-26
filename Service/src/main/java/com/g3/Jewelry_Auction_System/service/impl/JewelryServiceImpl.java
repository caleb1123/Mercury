package com.g3.Jewelry_Auction_System.service.impl;


import com.g3.Jewelry_Auction_System.converter.AuctionConverter;
import com.g3.Jewelry_Auction_System.converter.JewelryConverter;
import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AuctionDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryDTO;
import com.g3.Jewelry_Auction_System.payload.request.JewelryPageRequest;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;

import com.g3.Jewelry_Auction_System.service.JewelryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JewelryServiceImpl implements JewelryService {
    @Autowired
    JewelryRepository   jewelryRepository;
    @Autowired
    JewelryConverter    jewelryConverter;
    @Autowired
    JewelryCategoryRepository jewelryCategoryRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionConverter auctionConverter;

    @Override
    public JewelryDTO addJewelry(JewelryDTO jewelryDTO) {
        Jewelry newJewelry = jewelryConverter.toEntity(jewelryDTO);
        newJewelry.setStatus(false);
        Jewelry newJewelrySaved = jewelryRepository.save(newJewelry);
        return jewelryConverter.toDTO(newJewelrySaved);

    }

    @Override
    public void delistJewelry(int jewelryId) {
        Jewelry jewelry = jewelryRepository
                .findByJewelryId(jewelryId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        jewelry.setStatus(false);
        jewelryRepository.save(jewelry);
    }

    @Override
    public JewelryDTO updateJewelry(JewelryDTO jewelryDTO, int id) {
        Jewelry jewelry = jewelryRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
            jewelry.setDesigner(jewelryDTO.getDesigner());
            jewelry.setGemstone(jewelryDTO.getGemstone());
            jewelry.setImage(jewelryDTO.getImage());
            jewelry.setDescription(jewelryDTO.getDescription());
            jewelry.setCondition(jewelryDTO.getCondition());
            jewelry.setEstimate(jewelryDTO.getEstimate());
            jewelry.setStartingPrice(jewelryDTO.getStartingPrice());
            jewelry.setStatus(jewelryDTO.getStatus());
            jewelry.setJewelryCategory(jewelryCategoryRepository.getReferenceById(jewelryDTO.getJewelryCategoryId()));
            jewelryRepository.save(jewelry);
            return jewelryConverter.toDTO(jewelry);
    }

    @Override
    public Page<JewelryDTO> getAllJewelry(int offset) {
        Pageable jewelryPageable = new JewelryPageRequest(2, offset);
        Page<Jewelry> allPosts = jewelryRepository.findAll(jewelryPageable);
        List<JewelryDTO> jewelryDTOs = new ArrayList<>();
        for (Jewelry j : allPosts) {
            JewelryDTO dto = jewelryConverter.toDTO(j);
            jewelryDTOs.add(dto);
        }
        return new PageImpl<>(jewelryDTOs, jewelryPageable, allPosts.getTotalElements());
    }

    @Override
    public List<JewelryDTO> getAll() {
        List<JewelryDTO> jewelryDTOs = jewelryConverter.convertToJewelryDTOList(jewelryRepository.findAll());
        if (jewelryDTOs.isEmpty()) {
            throw new AppException(ErrorCode.LIST_EMPTY);
        }
        return jewelryDTOs;
    }

    @Override
    public List<JewelryDTO> searchName(String name) {
        List<JewelryDTO> list = jewelryConverter.convertToJewelryDTOList(jewelryRepository.getJewelriesByName(name));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public JewelryDTO getJewelryDetail(int jewelryId) {
        return jewelryConverter.toDTO(
                jewelryRepository.findByJewelryId(jewelryId).orElseThrow(
                        () -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED)
                )
        );
    }
    @Override
    public AuctionDTO getAuctionByJewelry(int jewelryId) {
        Jewelry jewelry = jewelryRepository
                .findByJewelryId(jewelryId).orElseThrow(
                () -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED)
        );
        List<Auction> auctions = auctionRepository.findByJewelry(jewelry);
        for (Auction auction : auctions) {
            if (auction.getStatus()) {
                return auctionConverter.toDTO(auction);
            }
        }
        throw new AppException(ErrorCode.AUCTION_NOT_FOUND);
    }
}

