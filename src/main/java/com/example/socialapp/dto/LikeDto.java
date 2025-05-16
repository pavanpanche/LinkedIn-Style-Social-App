package com.example.socialapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private Long postId;
    private Long userId;
    private boolean likedByCurrentUser;
}
