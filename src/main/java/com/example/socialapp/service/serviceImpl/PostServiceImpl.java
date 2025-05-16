package com.example.socialapp.service.serviceImpl;

import com.example.socialapp.dto.PostDto;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.PostRepository;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PostDto createPost(PostDto postDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        post = postRepository.save(post);

        return mapToDto(post);
    }
    @Override
    public PostDto updatePost(Long postId, PostDto postDto, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Compare logged-in user's email (username) with the post owner's email
        if (!post.getUser().getEmailId().equals(username)) {
            throw new AccessDeniedException("Unauthorized to delete this post");
        }



        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        return mapToDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getEmailId().equals(username)) {
            throw new AccessDeniedException("Unauthorized to delete this post");
        }

        postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public Page<PostDto> getHomeFeed(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToDto);
    }
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    @Override
    public Page<PostDto> getSortedPosts(String sortBy, String order, Pageable pageable) {
        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Post> sortedPosts = postRepository.findAll(sortedPageable);
        return sortedPosts.map(this::mapToDto);
    }


    private PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setUsername(post.getUser().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }
}
