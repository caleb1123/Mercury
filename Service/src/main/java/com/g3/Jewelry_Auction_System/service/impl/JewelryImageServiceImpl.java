package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.JewelryImageConverter;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryImageRepository;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.repository.PostImageRepository;
import com.g3.Jewelry_Auction_System.service.JewelryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JewelryImageServiceImpl implements JewelryImageService {
    @Autowired
    JewelryImageRepository jewelryImageRepository;
    @Autowired
    JewelryRepository jewelryRepository;
    @Autowired
    PostImageRepository postImageRepository;
    @Autowired
    JewelryImageConverter jewelryImageConverter;

    @Override
    public JewelryImageDTO addJewelryImage(JewelryImageDTO jewelryImageDTO) {
        Jewelry jewelry = jewelryRepository.findById(jewelryImageDTO.getJewelryId())
                .orElseThrow(() -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED));
        if (jewelryImageDTO.getJewelryImageURL().isEmpty()) {
            throw new IllegalArgumentException("Jewelry image URL is empty");
        }
        JewelryImage image = jewelryImageRepository.save(jewelryImageConverter.toEntity(jewelryImageDTO));
        return jewelryImageConverter.toDTO(image);
    }

    @Override
    public List<JewelryImageDTO> getImagesByJewelryId(int id) {
        List<JewelryImage> list = jewelryImageRepository.getByJewelryId(id);
        List<JewelryImageDTO> dtoList = new ArrayList<>();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.NO_IMAGE_FOUND);
        } else {
            for (JewelryImage jewelryImage : list) {
                dtoList.add(jewelryImageConverter.toDTO(jewelryImage));
            }
        }
        return dtoList;
    }
}
