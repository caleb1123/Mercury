package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostConverter;
import com.g3.Jewelry_Auction_System.converter.RoleConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import com.g3.Jewelry_Auction_System.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostConverter postConverter;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostCategoryRepository postCategoryRepository;
    @Autowired RoleConverter roleConverter;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Optional<Account> account  = accountRepository.findByUserName(name);
        if (name.equals("anonymousUser")) {
            throw new AppException(ErrorCode.NOT_LOGGED_IN);
        } else if (account.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else if (account.get().getRole().getRoleName().equals(ERole.USER)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        postDTO.setAccountId(account.get().getAccountId());
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

    @Override
    public List<PostDTO> getPostByCategory(String categoryName) {

        List<PostDTO> list = postConverter.convertToDTOList(postRepository.getPostsByCategoryName(categoryName));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.LIST_EMPTY);
        }
        if (list.contains(null)) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public List<PostDTO> getPostByCategory(int cateId) {
        List<PostDTO> list = postConverter.convertToDTOList(postRepository.getPostsByCategoryId(cateId));
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.LIST_EMPTY);
        }
        if (list.contains(null)) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public List<PostDTO> getActivePostByCategory(int cateId) {
        List<PostDTO> list = postConverter.convertToDTOList(postRepository.getPostsByCategoryId(cateId));
        for (PostDTO postDTO : list) {
            if (!postDTO.getStatus()) {
                list.remove(postDTO);
            }
        }
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.LIST_EMPTY);
        }
        if (list.contains(null)) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<PostDTO> list = postConverter.convertToDTOList(postRepository.findAll());
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<PostDTO> getAllActivePosts() {
        List<PostDTO> list = postConverter.convertToDTOList(postRepository.findAllThatActive());
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public PostDTO getPostById(int id) {
        PostDTO postDTO = postConverter.toDTO(postRepository.findById(id).get());
        if (postDTO == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return postDTO;
    }

    @Override
    public List<PostDTO> getPostByAccountId(int id) {
        List<Post> post = postRepository.getPostByAccountId(id);
        if (post == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        return postConverter.convertToDTOList(post);
    }
}