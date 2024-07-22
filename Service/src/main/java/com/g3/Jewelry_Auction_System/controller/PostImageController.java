package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.PostImage;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import com.g3.Jewelry_Auction_System.service.JewelryImageService;
import com.g3.Jewelry_Auction_System.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")

@RequestMapping("/postImage")
public class PostImageController {
    @Autowired
    PostImageService postImageService;
    @PostMapping("/upload/{postId}")
    public ResponseEntity<String> addPostImage(@RequestParam("file") MultipartFile file, @PathVariable int postId) {
        try {

            var imageUrl= postImageService.uploadImageToCloudinary(file,postId);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{postId}")
    public ResponseEntity<List<PostImageDTO>> getImagesByPostId(@PathVariable int postId) {
        List<PostImageDTO> list = postImageService.getImagesByPostId(postId);
        return new ResponseEntity<>(list , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileId) {
        try {
            boolean isDeleted = postImageService.deleteImage(fileId);
            if (isDeleted) {
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PostImageDTO getImageByPostId(@PathVariable int id) {
        PostImageDTO image = postImageService.getImageByPostId(id);
        return image;
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{id}/autoImg")
    public ResponseEntity<PostImageDTO> getImageAuto(@PathVariable int id) {
        PostImageDTO image = postImageService.getImageAuto(id);
        if(image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
