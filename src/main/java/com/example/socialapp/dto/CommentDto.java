package com.example.socialapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
        private Long id;

        @NotBlank(message = "Content must not be blank")
        @Size(max = 500, message = "Comment content must not exceed 500 characters")
        private String content;
        private String username;
        private Long postId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }


