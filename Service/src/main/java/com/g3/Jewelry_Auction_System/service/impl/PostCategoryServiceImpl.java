package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostCategoryConverter;
import com.g3.Jewelry_Auction_System.entity.PostCategory;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PostCategoryServiceImpl implements PostCategoryService {
    @Autowired
    private PostCategoryConverter postCategoryConverter;
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Override
    public PostCategoryDTO createPostCategory(PostCategoryDTO postCategoryDTO) {
        if (postCategoryDTO.getCategoryName().isEmpty()) throw new AppException(ErrorCode.EMPTY_FIELD);
        else {
            PostCategory postCategory = postCategoryConverter.toEntity(postCategoryDTO);
            postCategoryRepository.save(postCategory);
            return postCategoryDTO;
        }
    }

    @Override
    public void updatePostCategory(PostCategoryDTO postCategoryDTO, int id) {
        Optional<PostCategory> optionalPostCategory = postCategoryRepository.findById(id);
        if (optionalPostCategory.isPresent()) {
            PostCategory postCategory = optionalPostCategory.get();
            postCategory.setCategoryName(postCategoryDTO.getCategoryName());
            postCategoryRepository.save(postCategory);
        } else{
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public void deletePostCategory(int id) {
        Optional<PostCategory> optionalPostCategory = postCategoryRepository.findById(id);
        if (optionalPostCategory.isPresent()) {
            postCategoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("postCategory not found");
        }
    }
}