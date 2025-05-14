package com.example.socialapp.service.serviceImpl;

import com.example.socialapp.dto.UserProfileDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.entity.UserProfile;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.UserProfileRepository;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto createUserProfile(Long userId, UserProfileDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setHeadline(dto.getHeadline());
        profile.setAbout(dto.getAbout());
        profile.setSkills(dto.getSkills());

        userProfileRepository.save(profile);

        return mapToDto(profile);
    }

    @Override
    public UserProfileDto updateUserProfile(Long userId, UserProfileDto dto) {
        UserProfile profile = getProfileByUserId(userId);

        profile.setHeadline(dto.getHeadline());
        profile.setAbout(dto.getAbout());
        profile.setSkills(dto.getSkills());

        userProfileRepository.save(profile);

        return mapToDto(profile);
    }

    @Override
    public UserProfileDto patchUserProfile(Long userId, UserProfileDto dto) {
        UserProfile profile = getProfileByUserId(userId);

        // Only update fields that are provided (non-null)
        if (dto.getHeadline() != null) {
            profile.setHeadline(dto.getHeadline());
        }
        if (dto.getAbout() != null) {
            profile.setAbout(dto.getAbout());
        }
        if (dto.getSkills() != null) {
            profile.setSkills(dto.getSkills());
        }

        userProfileRepository.save(profile);

        return mapToDto(profile);
    }

    @Override
    public Optional<UserProfileDto> getUserProfile(Long userId) {
        UserProfile profile = getProfileByUserId(userId);
        return Optional.of(mapToDto(profile));
    }

    @Override
    public void deleteUserProfile(Long userId) {
        UserProfile profile = getProfileByUserId(userId);
        userProfileRepository.delete(profile);
    }

    // Helper method to get profile by userId
    private UserProfile getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return userProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user id " + userId));
    }

    // Helper method to convert entity to DTO
    private UserProfileDto mapToDto(UserProfile profile) {
        UserProfileDto dto = new UserProfileDto();
        dto.setHeadline(profile.getHeadline());
        dto.setAbout(profile.getAbout());
        dto.setSkills(profile.getSkills());
        return dto;
    }
}
