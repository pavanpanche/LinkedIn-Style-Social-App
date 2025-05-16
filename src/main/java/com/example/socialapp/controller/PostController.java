package com.example.socialapp.controller;

import com.example.socialapp.dto.PostDto;
import com.example.socialapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, Principal principal) {
        PostDto createdPost = postService.createPost(postDto, principal.getName());

        // Location header pointing to the new resource
        URI location = URI.create(String.format("/api/posts/%d", createdPost.getId()));
        return ResponseEntity.created(location).body(createdPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto, Principal principal) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto, principal.getName()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    @GetMapping("/feed")
    public ResponseEntity<Page<PostDto>> getHomeFeed(Pageable pageable) {
        return ResponseEntity.ok(postService.getHomeFeed(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }

    @GetMapping("/sorted")
    public ResponseEntity<Page<PostDto>> getSortedPosts(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getSortedPosts(sortBy, order, pageable));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
