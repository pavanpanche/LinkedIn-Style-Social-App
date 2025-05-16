package com.example.socialapp.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who created the comment

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // The post the comment is associated with

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;   // Time when the comment was created

    @Column
    private LocalDateTime updatedAt;
}