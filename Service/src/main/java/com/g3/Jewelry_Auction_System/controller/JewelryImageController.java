package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
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
    @CrossOrigin(origins = "http://localhost:3001")

    @PostMapping("/upload/{jewelryId}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,@PathVariable int jewelryId) {
        try {
            String imageUrl = jewelryImageService.uploadImageToGoogleDrive(file,jewelryId);
            String fileId = imageUrl.split("=")[1]; // Extract the file ID from the URL
            jewelryImageService.setFilePublic(fileId);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/add")
    public ResponseEntity<JewelryImageDTO> addJewelryImage(@RequestBody JewelryImageDTO jewelryImageDTO) {
        JewelryImageDTO newImage = jewelryImageService.addJewelryImage(jewelryImageDTO);
        return new ResponseEntity<>(newImage , HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{jewelryId}")
    public ResponseEntity<List<JewelryImageDTO>> getImageByJewelryId(@PathVariable int jewelryId) {
        List<JewelryImageDTO> list = jewelryImageService.getImagesByJewelryId(jewelryId);
        return new ResponseEntity<>(list , HttpStatus.OK);
    }
}
