package com.example.socialapp.service.serviceImpl;

import com.example.socialapp.dto.UserDto;
import com.example.socialapp.entity.User;
import com.example.socialapp.repository.UserRepository;
import com.example.socialapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired UserRepository userRepository;

    @Override
    public List<UserDto> searchUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .emailId(user.getEmailId())
                .username(user.getUsername())
                .build();
    }

}
