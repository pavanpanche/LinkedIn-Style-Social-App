package com.example.socialapp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeSummaryDto {
    private Long postId;
    private int totalLikes;
    private boolean likedByUser;
    private List<String> likedUsernames;
}
