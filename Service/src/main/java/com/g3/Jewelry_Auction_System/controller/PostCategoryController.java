package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postCategory")
public class PostCategoryController {
    @Autowired
    private PostCategoryService postCategoryService;

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getCategories")
    public ResponseEntity<List<PostCategoryDTO>> getAllCategories() {
        List<PostCategoryDTO> categories = postCategoryService.getAllPostCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}