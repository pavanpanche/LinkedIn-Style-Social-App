package com.example.socialapp.controller;

import com.example.socialapp.dto.UserProfileDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileService userProfileService;

    // Create profile for logged-in user
    @PostMapping
    public ResponseEntity<UserProfileDto> createProfile(@Valid @RequestBody UserProfileDto dto, Principal principal) {
        User user = getCurrentUser(principal);
        UserProfileDto createdProfile = userProfileService.createUserProfile(user.getId(), dto);

        // Build URI: /api/profile/{userId}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdProfile);
    }
    // Create profile for any user (admin use-case) - optional

    @PostMapping("/{userId}")
    public ResponseEntity<UserProfileDto> createProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileDto dto) {

        UserProfileDto created = userProfileService.createUserProfile(userId, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(created);
    }


    // Full update
    @PutMapping
    public ResponseEntity<UserProfileDto> updateProfile(@RequestBody UserProfileDto dto, Principal principal) {
        User user = getCurrentUser(principal);
        return ResponseEntity.ok(userProfileService.updateUserProfile(user.getId(), dto));
    }

    // Partial update
    @PatchMapping
    public ResponseEntity<UserProfileDto> patchProfile(@RequestBody UserProfileDto dto, Principal principal) {
        User user = getCurrentUser(principal);
        return ResponseEntity.ok(userProfileService.patchUserProfile(user.getId(), dto));
    }

    // Get profile
    @GetMapping
    public ResponseEntity<UserProfileDto> getProfile(Principal principal) {
        User user = getCurrentUser(principal);
        return userProfileService.getUserProfile(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete profile
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(Principal principal) {
        User user = getCurrentUser(principal);
        userProfileService.deleteUserProfile(user.getId());
        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByEmailId(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
