package com.example.socialapp.dto;

import lombok.Data;

@Data
public class LogInRequest {
    private String usernameOrEmail;
    private String password;
}
