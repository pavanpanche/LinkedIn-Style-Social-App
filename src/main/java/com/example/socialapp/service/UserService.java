package com.example.socialapp.service;
import com.example.socialapp.dto.UserDto;


import java.util.List;
public interface UserService {
    List<UserDto> searchUsersByName(String name);
}
