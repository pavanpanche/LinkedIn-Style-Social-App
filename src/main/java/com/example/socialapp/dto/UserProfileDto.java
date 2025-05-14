package com.example.socialapp.dto;
import lombok.*;
import jakarta.validation.constraints.NotBlank;


@Data
public class UserProfileDto {

    @NotBlank(message = "Headline is required")
    private String headline;

    @NotBlank(message = "About is required")
    private String about;

    @NotBlank(message = "Skills are required")
    private String skills;
}
