package com.g3.Jewelry_Auction_System.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    @Autowired
    Cloudinary cloudinary;



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

    @Override
    public List<JewelryImageDTO> getImagesByJewelryIdWithoutStatusFalse(int id) {
        List<JewelryImage> list = jewelryImageRepository.getByJewelryIdWithoutStatusFalse(id);
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


    @Override
    public String uploadImageToCloudinary(MultipartFile file, int id) throws IOException {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED));
        Integer imageCount = jewelryImageRepository.getImageCountByJewelryId(id);
        if(imageCount == null){
            imageCount = 0;
        }
        if ( imageCount >= 5) {
            throw new AppException(ErrorCode.IMAGE_MANY);
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        JewelryImage jewelryImage = new JewelryImage();
        jewelryImage.setJewelry(jewelry);
        jewelryImage.setJewelryImageURL((String) uploadResult.get("url"));
        jewelryImage.setStatus(true);
        jewelryImage.setFileId(getRandomNumber(8));
        jewelryImageRepository.save(jewelryImage);
        return (String) uploadResult.get("url");
    }

    @Override
    public boolean deleteImage(String fileId) throws IOException {
        JewelryImage jewelryImage = jewelryImageRepository.findByFileId(fileId);
        if (jewelryImage != null) {
            String publicId = extractPublicId(jewelryImage.getJewelryImageURL());
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (deleteResult.get("result").equals("ok")) {
                jewelryImage.setStatus(false);
                jewelryImageRepository.save(jewelryImage);
                return true;
            }
        }
        return false;
    }
    private String extractPublicId(String jewelryImageUrl) {
        // Example: https://res.cloudinary.com/demo/image/upload/v1234567890/public_id.jpg
        // Extract public_id "v1234567890/public_id"
        int startIndex = jewelryImageUrl.lastIndexOf("/") + 1;
        int endIndex = jewelryImageUrl.lastIndexOf(".");
        return jewelryImageUrl.substring(startIndex, endIndex);
    }
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }


    @Override
    public JewelryImageDTO getImageByFileId(String fileId) {
        JewelryImage image = jewelryImageRepository.findByFileId(fileId);
        if(image != null){
            return jewelryImageConverter.toDTO(image);
        }
        return null;
    }

    @Override
    public JewelryImageDTO getImageAuto(int id) {
        JewelryImage image = jewelryImageRepository.findJewelryImageAuto(id);
        return image != null ? jewelryImageConverter.toDTO(image) : null;
    }
}
