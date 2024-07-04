package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;

import java.util.List;

public interface PostCategoryService {
    List<PostCategoryDTO> getAllPostCategories();
}