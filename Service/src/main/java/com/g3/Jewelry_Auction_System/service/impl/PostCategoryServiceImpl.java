package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostCategoryConverter;
import com.g3.Jewelry_Auction_System.entity.PostCategory;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public PostCategoryDTO updatePostCategory(PostCategoryDTO postCategoryDTO, int id) {
        PostCategory postCategory = postCategoryRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_CATEGORY_NOT_FOUND));
        if (postCategory != null) {
            postCategory.setCategoryName(postCategoryDTO.getCategoryName());
        }
        postCategoryRepository.save(postCategory);
        return postCategoryConverter.toDTO(postCategory);
    }

    @Override
    public List<PostCategoryDTO> getPostByCategoryLike(String cate) {
        List<PostCategoryDTO> list = postCategoryConverter.convertToDTOList(postCategoryRepository.findAllByCateName(cate));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }
}