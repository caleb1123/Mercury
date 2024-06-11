package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    void updatePost(PostDTO postDTO, int id);
    void deletePost(int id);
}