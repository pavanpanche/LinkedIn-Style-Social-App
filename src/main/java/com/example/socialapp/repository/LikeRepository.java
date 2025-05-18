package com.example.socialapp.repository;

import com.example.socialapp.entity.Like;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostAndUser(Post post, User user);

    List<Like> findByPost(Post post);

    Page<Like> findByUser(User user, Pageable pageable);

    long countByPost(Post post);
}
