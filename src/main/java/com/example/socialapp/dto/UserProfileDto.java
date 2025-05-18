package com.example.socialapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileDto {

    @NotBlank(message = "Headline is required")
    @Size(min = 3, max = 255, message = "Headline must be between 3 and 255 characters")
    private String headline;

    @NotBlank(message = "About is required")
    @Size(min = 10, max = 1000, message = "About must be between 10 and 1000 characters")
    private String about;

    @NotBlank(message = "Skills are required")
    @Size(min = 3, max = 255, message = "Skills must be between 3 and 255 characters")
    private String skills;

    @NotBlank(message = "Education are required")
    @Size(min = 3, max = 255, message = "Education must be between 3 and 255 characters")
    private String education;

    @NotBlank(message = "Skills are required")
    @Size(min = 3, max = 255, message = "Location must be between 3 and 255 characters")
    private String location;






}
