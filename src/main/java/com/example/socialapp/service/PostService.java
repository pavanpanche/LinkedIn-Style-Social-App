package com.example.socialapp.service;

import com.example.socialapp.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String username);
    PostDto updatePost(Long postId, PostDto postDto, String username);
    void deletePost(Long postId, String username);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long postId);
}

