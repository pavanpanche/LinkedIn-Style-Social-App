package com.example.socialapp.controller;

import com.example.socialapp.dto.UserProfileDto;
import com.example.socialapp.dto.UserProfilePatchDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.security.SecurityUtil;
import com.example.socialapp.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private SecurityUtil securityUtil;

    @Operation(summary = "Create a profile for the logged-in user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UserProfileDto> createProfile(@RequestBody @Valid UserProfileDto dto, Principal principal) {
        User user = securityUtil.getCurrentUser(principal);
        UserProfileDto createdProfile = userProfileService.createUserProfile(user.getId(), dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdProfile);
    }

    @Operation(summary = "Fully update the profile of the logged-in user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<UserProfileDto> updateProfile(@RequestBody @Valid UserProfileDto dto, Principal principal) {
        User user = securityUtil.getCurrentUser(principal);
        return ResponseEntity.ok(userProfileService.updateUserProfile(user.getId(), dto));
    }

    @Operation(summary = "Partially update the profile of the logged-in user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile patched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('USER')")
    @PatchMapping
    public ResponseEntity<UserProfileDto> patchProfile(@RequestBody UserProfilePatchDto patchDto, Principal principal) {
        User user = securityUtil.getCurrentUser(principal);
        UserProfileDto updated = userProfileService.patchUserProfile(user.getId(), patchDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get the profile of the logged-in user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @GetMapping
    public ResponseEntity<UserProfileDto> getProfile(Principal principal) {
        User user = securityUtil.getCurrentUser(principal);
        return userProfileService.getUserProfile(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
