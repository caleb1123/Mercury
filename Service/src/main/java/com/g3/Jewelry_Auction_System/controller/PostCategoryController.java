package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.entity.PostCategory;
import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
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
    @PostMapping("/create")
    public ResponseEntity<PostCategoryDTO> createPostCategory(@RequestBody PostCategoryDTO postCategoryDTO) {
        PostCategoryDTO createdPostCategoryDTO = postCategoryService.createPostCategory(postCategoryDTO);
        return new ResponseEntity<>(createdPostCategoryDTO, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<PostCategoryDTO> updatePostCategory(@RequestBody PostCategoryDTO postCategoryDTO, @PathVariable(value = "categoryId") int categoryId) {
        PostCategoryDTO postCategoryDTO1 = postCategoryService.updatePostCategory(postCategoryDTO, categoryId);

        if (postCategoryDTO1 != null) {
            return new ResponseEntity<>(postCategoryDTO1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/search/{input}")
    public ResponseEntity<List<PostCategoryDTO>> searchPost(@PathVariable(value = "input") String input) {
        List<PostCategoryDTO> postCategoryDTOList = postCategoryService.getPostByCategoryLike(input);
        if (postCategoryDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postCategoryDTOList, HttpStatus.OK);
    }
}