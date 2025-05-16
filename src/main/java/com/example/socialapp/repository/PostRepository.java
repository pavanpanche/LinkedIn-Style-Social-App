package com.example.socialapp.repository;

import com.example.socialapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByUser_Username(String username);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

}
