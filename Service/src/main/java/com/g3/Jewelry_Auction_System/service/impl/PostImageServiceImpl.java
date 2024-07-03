package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostImageConverter;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.entity.PostImage;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostImageDTO;
import com.g3.Jewelry_Auction_System.repository.PostImageRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import com.g3.Jewelry_Auction_System.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostImageServiceImpl implements PostImageService {
    @Autowired
    PostImageRepository postImageRepository;
    @Autowired
    PostImageConverter postImageConverter;
    @Autowired
    PostRepository postRepository;

    @Override
    public PostImageDTO addPostImage(PostImageDTO postImageDTO) {
        postRepository.findById(postImageDTO.getPostId()).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        if (postImageDTO.getPostImageURL().isEmpty()) {
            throw new IllegalArgumentException("Post image URL is empty");
        }
        PostImage image = postImageConverter.toEntity(postImageDTO);
        postImageRepository.save(image);
        return postImageConverter.toDTO(image);
    }
    @Override
    public List<PostImageDTO> getImagesByPostId(int id) {
        List<PostImage> list = postImageRepository.getByPostId(id);
        List<PostImageDTO> dtoList = new ArrayList<>();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.NO_IMAGE_FOUND);
        } else {
            for (PostImage postImage : list) {
                dtoList.add(postImageConverter.toDTO(postImage));
            }
        }
        return dtoList;
    }
}
