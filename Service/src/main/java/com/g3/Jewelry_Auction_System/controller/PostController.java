package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(value = "postId") int postId) {
            PostDTO postDTO1 = postService.updatePost(postDTO, postId);
            if (postDTO1 != null) {
                return new ResponseEntity<>(postDTO1, HttpStatus.OK);
            }
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable(value = "postId") int postId){
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{input}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable(value = "input") String input){
        List<PostDTO> postDTOList = postService.getPostByNameLike(input);
        if(postDTOList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

}