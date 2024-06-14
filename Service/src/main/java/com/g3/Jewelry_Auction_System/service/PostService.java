package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO updatePost(PostDTO postDTO, int id);
    void deletePost(int id);
    List<PostDTO> getPostByNameLike(String title);
}