package com.example.socialapp.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long postId;
    private String text;
}
