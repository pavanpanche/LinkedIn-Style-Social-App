package com.example.socialapp.service;
import com.example.socialapp.dto.UserProfileDto;


import java.util.Optional;

public interface UserProfileService {

    // Create a new profile
    UserProfileDto createUserProfile(Long userId, UserProfileDto dto);
    // Full update of profile (replace all fields)
    UserProfileDto updateUserProfile(Long userId, UserProfileDto dto);

    // Partial update (only modify provided fields)
    UserProfileDto patchUserProfile(Long userId, UserProfileDto dto);

    // Get profile by user ID
    Optional<UserProfileDto> getUserProfile(Long userId);

    // Delete profile
    void deleteUserProfile(Long userId);
}
