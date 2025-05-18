package com.example.socialapp.repository;

import com.example.socialapp.entity.User;
import com.example.socialapp.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
    Optional<UserProfile> findByUser_Id(Long userId);
}
