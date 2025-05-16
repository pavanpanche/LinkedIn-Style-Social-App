package com.example.socialapp.service.serviceImpl;

import com.example.socialapp.dto.CommentDto;
import com.example.socialapp.entity.Comment;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.CommentRepository;
import com.example.socialapp.repository.PostRepository;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public CommentDto addComment(Long postId, String username, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .post(post)
                .user(user)
                .build();

        commentRepository.save(comment);

        return toDto(comment);
    }

    @Override
    public CommentDto updateComment(Long commentId, String username, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this comment");
        }

        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);

        return toDto(comment);
    }

    @Override
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return commentRepository.findByPost(post).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentDto> getCommentsByPost(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return commentRepository.findByPost(post, pageable)
                .map(this::toDto);
    }

    private CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUsername(comment.getUser().getUsername());
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
