package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JewelryImageService {

    List<JewelryImageDTO> getImagesByJewelryId(int id);
    List<JewelryImageDTO> getImagesByJewelryIdWithoutStatusFalse(int id);
    String uploadImageToCloudinary(MultipartFile file, int id) throws IOException;
    boolean deleteImage(String fileId) throws IOException;
    JewelryImageDTO getImageByFileId(String fileId);
    JewelryImageDTO getImageAuto(int id);
}
