package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;

public interface PostCategoryService {
    PostCategoryDTO createPostCategory(PostCategoryDTO postCategoryDTO);
    void updatePostCategory(PostCategoryDTO postCategoryDTO, int id);
    void deletePostCategory(int id);
}