package com.example.socialapp.repository;
import com.example.socialapp.entity.Comment;
import com.example.socialapp.entity.Post;
import com.example.socialapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface CommentRepository extends JpaRepository<Comment, Long> {
        List<Comment> findByPost(Post post);       //Get all comments for a post

        Page<Comment> findByPost(Post post, Pageable pageable);  //Paginated version for comments on a post

        List<Comment> findByUser(User user);      //Fetch all comments made by a specific user
}

