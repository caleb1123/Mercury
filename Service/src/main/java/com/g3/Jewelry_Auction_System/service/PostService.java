package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO updatePost(PostDTO postDTO, int id);
    PostDTO deletePost(int id);
    List<PostDTO> getPostByTitleLike(String title);
    List<PostDTO> getPostByCategory(String cate);
    List<PostDTO> getPostByCategory(int cateId);
    List<PostDTO> getActivePostByCategory(int cateId);
    List<PostDTO> getAllPosts();
    List<PostDTO> getAllActivePosts();
    PostDTO getPostById(int id);
    List<PostDTO> getPostByAccountId(int id);
}