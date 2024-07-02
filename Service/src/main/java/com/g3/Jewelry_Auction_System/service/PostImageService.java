package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;

import java.util.List;

public interface PostImageService {
    PostImageDTO addPostImage(PostImageDTO postImageDTO);

    List<PostImageDTO> getImagesByPostId(int id);
}
