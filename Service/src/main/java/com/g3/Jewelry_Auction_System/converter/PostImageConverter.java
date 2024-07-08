package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import com.g3.Jewelry_Auction_System.entity.PostImage;
import com.g3.Jewelry_Auction_System.payload.DTO.JewelryImageDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import com.g3.Jewelry_Auction_System.repository.JewelryRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostImageConverter {
    @Autowired
    PostRepository postRepository;

    public PostImage toEntity(PostImageDTO postImageDTO) {
        if (postImageDTO == null) return null;
        PostImage entity = new PostImage();
        entity.setPostImageId(postImageDTO.getPostImageId());
        entity.setPostImageURL(postImageDTO.getPostImageURL());
        entity.setPost(postRepository.getReferenceById(postImageDTO.getPostId()));
        entity.setFileId(postImageDTO.getFileId());
        entity.setStatus(postImageDTO.isStatus());
        return entity;
    }
    public PostImageDTO toDTO(PostImage postImage) {
        if (postImage == null) return null;
        PostImageDTO dto = new PostImageDTO();
        dto.setPostImageId(postImage.getPostImageId());
        dto.setPostImageURL(postImage.getPostImageURL());
        dto.setPostId(postImage.getPost().getPostId());
        dto.setFileId(postImage.getFileId());
        dto.setStatus(postImage.isStatus());
        return dto;
    }
}
