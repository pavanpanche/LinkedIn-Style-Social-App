package com.example.socialapp.service;

import com.example.socialapp.entity.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    User getUserByUsername(String username);
}
