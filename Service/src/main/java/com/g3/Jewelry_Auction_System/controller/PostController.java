package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")

@RequestMapping("posts")
public class PostController {
    @Autowired
    private PostService postService;

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(value = "postId") int postId) {
        PostDTO postDTO1 = postService.updatePost(postDTO, postId);
        if (postDTO1 != null) {
            return new ResponseEntity<>(postDTO1, HttpStatus.OK);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/delete/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable(value = "postId") int postId) {
        PostDTO deletePost = postService.deletePost(postId);
        if (deletePost != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/search/{input}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable(value = "input") String input) {
        List<PostDTO> postDTOList = postService.getPostByTitleLike(input);
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/searchByCate/{categoryName}")
    public ResponseEntity<List<PostDTO>> searchPostByCate(@PathVariable(value = "categoryName") String categoryName) {
        List<PostDTO> postDTOList = postService.getPostByCategory(categoryName);
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/searchByCateID/{categoryId}")
    public ResponseEntity<List<PostDTO>> searchPostByCateID(@PathVariable(value = "categoryId") int categoryId) {
        List<PostDTO> postDTOList = postService.getPostByCategory(categoryId);
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getActivePostByCate/{categoryId}")
    public ResponseEntity<List<PostDTO>> getActivePostByCate(@PathVariable(value = "categoryId") int categoryId) {
        List<PostDTO> postDTOList = postService.getActivePostByCategory(categoryId);
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getAll")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postDTOList = postService.getAllPosts();
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getAllActive")
    public ResponseEntity<List<PostDTO>> getAllActivePosts() {
        List<PostDTO> postDTOList = postService.getAllActivePosts();
        if (postDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
        PostDTO postDTO = postService.getPostById(id);
        if (postDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/getByAccountId/{id}")
    public ResponseEntity<List<PostDTO>> getPostByAccountId(@PathVariable int id) {
        List<PostDTO> postDTO = postService.getPostByAccountId(id);
        if (postDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
}
