package com.example.socialapp.service;

import com.example.socialapp.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long postId, String username, CommentDto commentDto);

    CommentDto updateComment(Long commentId, String username, CommentDto commentDto);

    void deleteComment(Long commentId, String username);

    List<CommentDto> getCommentsByPost(Long postId);

    Page<CommentDto> getCommentsByPost(Long postId, Pageable pageable);
}
