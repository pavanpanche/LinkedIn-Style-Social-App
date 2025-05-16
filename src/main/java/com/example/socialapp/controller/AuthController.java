package com.example.socialapp.controller;

import com.example.socialapp.dto.AuthRequest;
import com.example.socialapp.dto.AuthResponse;
import com.example.socialapp.dto.RegisterRequest;
import com.example.socialapp.entity.User;
import com.example.socialapp.security.CustomUserDetails;
import com.example.socialapp.security.JwtService;
import com.example.socialapp.service.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"api/auth", "/"})

@RequiredArgsConstructor
public class AuthController {

    private final UserInfo userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        String token = jwtService.generateToken(new CustomUserDetails(user));
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    @GetMapping("/")
    public String home() {
        return "Welcome to LinkedIn Social App Backend!";
    }
}
