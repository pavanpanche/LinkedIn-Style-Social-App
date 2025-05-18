package com.example.socialapp.dto;

import lombok.Data;


@Data
public class RegistrationRequestDto {
    private String username;
    private String emailId;
    private String password;
    private String fullName;
}