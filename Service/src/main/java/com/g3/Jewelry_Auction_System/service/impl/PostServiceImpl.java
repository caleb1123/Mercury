package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.PostConverter;
import com.g3.Jewelry_Auction_System.entity.Post;
import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.PostCategoryRepository;
import com.g3.Jewelry_Auction_System.repository.PostRepository;
import com.g3.Jewelry_Auction_System.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        return postDTO;
    }

    @Override
    public void updatePost(PostDTO postDTO, int id) {
        Optional<Post> opPost = postRepository.findById(id);
        if (opPost.isPresent()) {
            Post post = opPost.get();
            post.setTitle(postDTO.getTitle());
            post.setPostDate(LocalDate.now());
            post.setContent(postDTO.getContent());
            post.setStatus(postDTO.getStatus());
            post.setAccount(accountRepository.getReferenceById((postDTO.getAccountId())));
            post.setPostCategory(postCategoryRepository.getReferenceById(postDTO.getCategoryId()));
            postRepository.save(post);
        } else{
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public void deletePost(int id){
        Optional<Post> opPost = postRepository.findById(id);
        if (opPost.isPresent()) {
            Post post = opPost.get();
            post.setStatus(false);
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public List<PostDTO> getPostByNameLike(String title) {
        return postConverter.convertToDTOList(postRepository.findByTitle(title));
    }
}