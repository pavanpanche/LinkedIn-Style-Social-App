package com.example.socialapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
