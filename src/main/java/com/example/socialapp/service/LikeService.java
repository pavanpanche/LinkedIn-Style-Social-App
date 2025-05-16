package com.example.socialapp.service;

import com.example.socialapp.dto.LikeSummaryDto;
import com.example.socialapp.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeService {
    void likePost(Long postId, String username);
    void dislikePost(Long postId, String username);
    Page<PostDto> getLikedPosts(String username, Pageable pageable);
    LikeSummaryDto getPostLikeSummary(Long postId, String username);
}
