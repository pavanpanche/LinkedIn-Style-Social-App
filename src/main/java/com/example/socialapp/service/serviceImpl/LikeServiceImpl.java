package com.example.socialapp.service.serviceImpl;
import com.example.socialapp.mapper.DtoMapper;
import com.example.socialapp.dto.LikeSummaryDto;
import com.example.socialapp.dto.PostDto;
import com.example.socialapp.entity.Like;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.LikeRepository;
import com.example.socialapp.repository.PostRepository;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired private LikeRepository likeRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired  private DtoMapper dtoMapper;

    @Override
    public void likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        likeRepository.findByPostAndUser(post, user)
                .ifPresentOrElse(
                        like -> {}, // Already liked
                        () -> likeRepository.save(Like.builder().post(post).user(user).build())
                );
    }

    @Override
    public void dislikePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        likeRepository.findByPostAndUser(post, user)
                .ifPresent(likeRepository::delete);
    }

    @Override
    public Page<PostDto> getLikedPosts(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return likeRepository.findByUser(user, pageable)
                .map(like -> dtoMapper.toPostDto(like.getPost()));
    }

    @Override
    public LikeSummaryDto getPostLikeSummary(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Like> likes = likeRepository.findByPost(post);
        List<String> usernames = likes.stream()
                .map(l -> l.getUser().getUsername())
                .collect(Collectors.toList());

        boolean likedByCurrentUser = likes.stream().anyMatch(l -> l.getUser().equals(user));

        return LikeSummaryDto.builder()
                .postId(postId)
                .totalLikes(likes.size())
                .likedByUser(likedByCurrentUser)
                .likedUsernames(usernames)
                .build();
    }
}
