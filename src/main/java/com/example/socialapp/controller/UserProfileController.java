package com.example.socialapp.controller;

import com.example.socialapp.dto.UserProfileDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<UserProfileDto> createProfile(
            @Valid @RequestBody UserProfileDto dto,
            Principal principal) {

        // Fetch the current user from the database using their email/username
        User user = userRepository.findByEmailId(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create the user profile by passing the userId and profile DTO
        UserProfileDto createdProfile = userProfileService.createUserProfile(user.getId(), dto);

        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }



    // Create Profile
    @PostMapping("/{userId}")
    public ResponseEntity<UserProfileDto> createProfile(@PathVariable Long userId, @RequestBody UserProfileDto dto) {
        UserProfileDto created = userProfileService.createUserProfile(userId, dto);
        return ResponseEntity.ok(created);
    }

    // Full Update (PUT)
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileDto> updateProfile(@PathVariable Long userId, @RequestBody UserProfileDto dto) {
        UserProfileDto updated = userProfileService.updateUserProfile(userId, dto);
        return ResponseEntity.ok(updated);
    }

    // Partial Update (PATCH)
    @PatchMapping("/{userId}")
    public ResponseEntity<UserProfileDto> patchProfile(@PathVariable Long userId, @RequestBody UserProfileDto dto) {
        UserProfileDto patched = userProfileService.patchUserProfile(userId, dto);
        return ResponseEntity.ok(patched);
    }

    // Get Profile
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        userProfileService.deleteUserProfile(userId);
        return ResponseEntity.noContent().build();
    }

}
