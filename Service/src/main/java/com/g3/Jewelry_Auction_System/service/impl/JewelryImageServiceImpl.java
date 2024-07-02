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
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File; // Ensure this import is correct

import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    Drive drive;

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
    @Override
    public String uploadImageToGoogleDrive(MultipartFile file, int id) throws IOException {
        Jewelry jewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JEWELRY_NOT_EXISTED));
        if(jewelry!= null){
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(file.getOriginalFilename());

            ByteArrayContent mediaContent = new ByteArrayContent(file.getContentType(), file.getBytes());

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            JewelryImage jewelryImage = new JewelryImage();
            jewelryImage.setJewelry(jewelry);
            jewelryImage.setJewelryImageURL("https://drive.google.com/uc?id=" + uploadedFile.getId());
            jewelryImageRepository.save(jewelryImage);
            return "https://drive.google.com/uc?id=" + uploadedFile.getId();
        }else {
            return "upload fail";
        }

    }
    @Override
    public void setFilePublic(String fileId) throws IOException {
        // Create a permission object with 'anyone' type and 'reader' role
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");

        // Create a permission request
        Drive.Permissions.Create permissionRequest = drive.permissions().create(fileId, permission);

        // Execute the request
        permissionRequest.execute();
    }
}