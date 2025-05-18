package com.example.socialapp.security;


import com.example.socialapp.entity.User;
import com.example.socialapp.exception.ResourceNotFoundException;
import com.example.socialapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(Principal principal) {
        String identity = principal.getName();
        return userRepository.findByUsernameOrEmailId(identity, identity)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
