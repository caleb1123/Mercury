package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryImageRepository;
import com.g3.Jewelry_Auction_System.service.JewelryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jewelryImage")
public class JewelryImageController {
    @Autowired
    JewelryImageService jewelryImageService;


    @Autowired
    JewelryImageRepository jewelryImageRepository;
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/upload/{jewelryId}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,@PathVariable int jewelryId) {
        try {
            String imageUrl = jewelryImageService.uploadImageToGoogleDrive(file,jewelryId);
            String fileId = imageUrl.split("=")[1]; // Extract the file ID from the URL
            jewelryImageService.setFilePublic(fileId);
             JewelryImage jewelry = jewelryImageRepository.findByFileId(fileId);
            jewelry.setFileId(fileId);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }


    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{jewelryId}")
    public ResponseEntity<List<JewelryImageDTO>> getImageByJewelryId(@PathVariable int jewelryId) {
        List<JewelryImageDTO> list = jewelryImageService.getImagesByJewelryId(jewelryId);
        return new ResponseEntity<>(list , HttpStatus.OK);
    }


    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileId) {
        try {
            boolean isDeleted = jewelryImageService.deleteImage(fileId);
            if (isDeleted) {
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image: " + e.getMessage());
        }
    }

}
