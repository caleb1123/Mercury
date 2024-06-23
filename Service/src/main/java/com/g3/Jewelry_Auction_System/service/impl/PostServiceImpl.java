package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostConverter;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import com.g3.Jewelry_Auction_System.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostConverter postConverter;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = postConverter.toEntity(postDTO);
        post.setPostDate(LocalDate.now());
        postRepository.save(post);
        return postConverter.toDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        post.setTitle(postDTO.getTitle());
        post.setPostDate(LocalDate.now());
        post.setContent(postDTO.getContent());
        post.setStatus(postDTO.getStatus());
        post.setAccount(accountRepository.getReferenceById((postDTO.getAccountId())));
        post.setPostCategory(postCategoryRepository.getReferenceById(postDTO.getCategoryId()));
        postRepository.save(post);
        return postConverter.toDTO(post);
    }

    @Override
    public PostDTO deletePost(int id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        post.setStatus(false);
        postRepository.save(post);
        return postConverter.toDTO(post);
    }

    @Override
    public List<PostDTO> getPostByTitleLike(String title) {
        List<PostDTO> list = postConverter.convertToDTOList(postRepository.findByTitle(title));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }
}