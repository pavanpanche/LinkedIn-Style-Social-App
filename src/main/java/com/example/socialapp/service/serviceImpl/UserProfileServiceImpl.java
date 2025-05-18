package com.example.socialapp.service.serviceImpl;

import com.example.socialapp.dto.UserProfileDto;
import com.example.socialapp.dto.UserProfilePatchDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.entity.UserProfile;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.UserProfileRepository;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    @Transactional
    public UserProfileDto createUserProfile(Long userId, UserProfileDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setHeadline(dto.getHeadline());
        profile.setAbout(dto.getAbout());
        profile.setSkills(dto.getSkills());
        profile.setEducation(dto.getEducation());
        profile.setLocation(dto.getLocation());

        UserProfile saved = userProfileRepository.save(profile);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(Long userId, UserProfileDto dto) {
        UserProfile profile = getProfileByUserId(userId);
        profile.setHeadline(dto.getHeadline());
        profile.setAbout(dto.getAbout());
        profile.setSkills(dto.getSkills());
        profile.setEducation(dto.getEducation());
        profile.setLocation(dto.getLocation());
        return mapToDto(userProfileRepository.save(profile));
    }

    @Override
    @Transactional
    public UserProfileDto patchUserProfile(Long userId, UserProfilePatchDto patchDto) {
        UserProfile profile = userProfileRepository.findByUserUserid(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        if (patchDto.getHeadline() != null) profile.setHeadline(patchDto.getHeadline());
        if (patchDto.getAbout() != null) profile.setAbout(patchDto.getAbout());
        if (patchDto.getSkills() != null) profile.setSkills(patchDto.getSkills());
        if (patchDto.getEducation() != null) profile.setEducation(patchDto.getEducation());
        if (patchDto.getLocation() != null) profile.setLocation(patchDto.getLocation());

        UserProfile updated = userProfileRepository.save(profile);
        return mapToDto(updated);
    }

    @Override
    public Optional<UserProfileDto> getUserProfile(Long userId) {
        UserProfile profile = getProfileByUserId(userId);
        return Optional.of(mapToDto(profile));
    }

    private UserProfile getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return userProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user id " + userId));
    }

    private UserProfileDto mapToDto(UserProfile profile) {
        UserProfileDto dto = new UserProfileDto();
        dto.setHeadline(profile.getHeadline());
        dto.setAbout(profile.getAbout());
        dto.setSkills(profile.getSkills());
        dto.setEducation(profile.getEducation());
        dto.setLocation(profile.getLocation());
        return dto;
    }
}
