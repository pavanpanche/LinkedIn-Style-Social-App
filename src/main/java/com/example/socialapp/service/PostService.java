package com.example.socialapp.service;

import com.example.socialapp.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String username);
    PostDto updatePost(Long postId, PostDto postDto, String username);
    void deletePost(Long postId, String username);
    PostDto getPostById(Long postId);
    List<PostDto> getAllPosts();
    Page<PostDto> getHomeFeed(Pageable pageable);
    List<PostDto> searchPosts(String keyword);
    Page<PostDto> getSortedPosts(String sortBy, String order, Pageable pageable);

}

