package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.EPostCategory;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.entity.PostCategory;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostCategoryConverter {
    public PostCategory toEntity(PostCategoryDTO dto){
        if (dto == null) return null;
        PostCategory entity = new PostCategory();
        entity.setCategoryId(dto.getCategoryId());
        entity.setCategoryName(EPostCategory.valueOf(dto.getCategoryName()));
        return entity;
    }

    public PostCategoryDTO toDTO(PostCategory entity){
        if (entity == null) return null;
        PostCategoryDTO dto = new PostCategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryName(entity.getCategoryName().name());
        return dto;
    }

    public List<PostCategoryDTO> convertToDTOList(List<PostCategory> postCs){
        List<PostCategoryDTO> postCategoryDTOList = new ArrayList<>();
        for (PostCategory postC : postCs){
            postCategoryDTOList.add(toDTO(postC));
        }
        return postCategoryDTOList;
    }

    public List<PostCategoryDTO> convertToDTOList(){
        List<PostCategoryDTO> postCategoryDTOList = new ArrayList<>();
        for (EPostCategory postC : EPostCategory.values()){
            PostCategoryDTO dto = new PostCategoryDTO(postC.getId(), postC.getDisplayName());
            postCategoryDTOList.add(dto);
        }
        return postCategoryDTOList;
    }
}
