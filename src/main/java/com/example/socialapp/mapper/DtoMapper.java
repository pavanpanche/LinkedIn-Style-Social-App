package com.example.socialapp.mapper;

import com.example.socialapp.dto.LikeSummaryDto;
import com.example.socialapp.dto.PostDto;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class DtoMapper {

    // Convert Post entity to PostDto
    public PostDto toPostDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setUsername(post.getUser().getUsername());
        return dto;
    }


    // Convert Post entity and current user to LikeSummaryDto

    public LikeSummaryDto toLikeSummaryDto(Post post, User currentUser) {
        LikeSummaryDto dto = new LikeSummaryDto();
        dto.setPostId(post.getId());
        dto.setTotalLikes(post.getLikes().size());
        dto.setLikedByUser(post.getLikes().stream()
                .anyMatch(like -> like.getUser().getUserid().equals(currentUser.getUserid()))
        );
        dto.setLikedUsernames(
                post.getLikes().stream()
                        .map(like -> like.getUser().getUsername())
                        .collect(Collectors.toList())
        );
        return dto;
    }

}
