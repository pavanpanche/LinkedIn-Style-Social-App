package com.example.socialapp.controller;
import com.example.socialapp.dto.UserDto;
import com.example.socialapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Search User by name
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchUsersByName(name));
    }
}
