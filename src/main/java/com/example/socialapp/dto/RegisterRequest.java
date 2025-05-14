package com.example.socialapp.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String emailId;
    private String password;
    private String fullName;
}