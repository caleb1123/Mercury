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
    public ResponseEntity<String> uploadImageCloudinary(@RequestParam("file") MultipartFile file,@PathVariable int jewelryId) throws IOException {
        try {

            var imageUrl= jewelryImageService.uploadImageToCloudinary(file,jewelryId);
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

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/all/{jewelryId}")
    public ResponseEntity<List<JewelryImageDTO>> getImageByJewelryIdWithoutStatus(@PathVariable int jewelryId) {
        List<JewelryImageDTO> list = jewelryImageService.getImagesByJewelryIdWithoutStatusFalse(jewelryId);
        return new ResponseEntity<>(list , HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3001")
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

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{fileId}")
    public ResponseEntity<JewelryImageDTO> getImageByFileId(@PathVariable String fileId) {
        JewelryImageDTO imageDTO = jewelryImageService.getImageByFileId(fileId);
        if (imageDTO != null) {
            return ResponseEntity.ok(imageDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/jewelry/{id}/image")
    public ResponseEntity<JewelryImageDTO> getImageAuto(@PathVariable int id) {
        JewelryImageDTO image = jewelryImageService.getImageAuto(id);
        if(image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
