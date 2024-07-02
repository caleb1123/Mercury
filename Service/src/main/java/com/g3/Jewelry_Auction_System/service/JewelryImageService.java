package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JewelryImageService {
    JewelryImageDTO addJewelryImage(JewelryImageDTO jewelryImageDTO);

    List<JewelryImageDTO> getImagesByJewelryId(int id);
    String uploadImageToGoogleDrive(MultipartFile file,int id) throws IOException;
    void setFilePublic(String fileId) throws IOException;

    boolean deleteImage(String fileId) throws IOException;
}
