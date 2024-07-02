package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostConverter {
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostCategoryConverter postCategoryConverter;
    @Autowired
    PostCategoryRepository postCategoryRepository;

    public Post toEntity(PostDTO dto){
        if (dto==null) return null;
        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setTitle(dto.getTitle());
        post.setPostDate(dto.getPostDate());
        post.setContent(dto.getContent());
        post.setStatus(dto.getStatus());
        post.setPostCategory(postCategoryRepository.getReferenceById(dto.getCategoryId()));
        post.setAccount(accountRepository.getReferenceById(dto.getAccountId()));
        return post;
    }
    public PostDTO toDTO(Post post){
        if (post==null) return null;
        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setPostDate(post.getPostDate());
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus());
        dto.setCategoryId(postCategoryConverter.toDTO(post.getPostCategory()).getCategoryId());
        dto.setAccountId(accountConverter.toDTO(post.getAccount()).getAccountId());
        return dto;
    }

    public List<PostDTO> convertToDTOList(List<Post> posts){
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : posts){
            postDTOList.add(toDTO(post));
        }
        return postDTOList;
    }
}
