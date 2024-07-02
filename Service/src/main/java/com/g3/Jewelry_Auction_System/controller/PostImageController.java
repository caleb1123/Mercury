package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import com.g3.Jewelry_Auction_System.service.JewelryImageService;
import com.g3.Jewelry_Auction_System.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postImage")
public class PostImageController {
    @Autowired
    PostImageService postImageService;
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/add")
    public ResponseEntity<PostImageDTO> addPostImage(@RequestBody PostImageDTO postImageDTO) {
        PostImageDTO newImage = postImageService.addPostImage(postImageDTO);
        return new ResponseEntity<>(newImage , HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list/{jewelryId}")
    public ResponseEntity<List<PostImageDTO>> getImageByPostId(@PathVariable int postId) {
        List<PostImageDTO> list = postImageService.getImagesByPostId(postId);
        return new ResponseEntity<>(list , HttpStatus.OK);
    }
}
