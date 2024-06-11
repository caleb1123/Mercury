package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequestMapping("/postCategory")
public class PostCategoryController {
    @Autowired
    private PostCategoryService postCategoryService;
    @PostMapping
    public ResponseEntity<PostCategoryDTO> createPostCategory(@RequestBody PostCategoryDTO postCategoryDTO) {
        PostCategoryDTO createdPostCategoryDTO = postCategoryService.createPostCategory(postCategoryDTO);
        return new ResponseEntity<>(createdPostCategoryDTO, HttpStatus.CREATED);
    }
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<PostCategoryDTO> updatePostCategory(@RequestBody PostCategoryDTO postCategoryDTO, @PathVariable(value = "categoryId") int categoryId) {
        try {
            postCategoryService.updatePostCategory(postCategoryDTO, categoryId);
            return ResponseEntity.ok().build();
        } catch (AppException e) {
            if (e.getErrorCode() == ErrorCode.POST_CATEGORY_NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Trả về 404
            } else if (e.getErrorCode() == ErrorCode.EMPTY_FIELD) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Trả về 400
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Trả về 500 - lỗi khác
            }
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Void> deletePostCategory(@PathVariable(value = "categoryId") int categoryId) {
        try {
            postCategoryService.deletePostCategory(categoryId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}