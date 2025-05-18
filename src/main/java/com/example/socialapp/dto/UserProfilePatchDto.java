package com.example.socialapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfilePatchDto {

    // partially update any field from here

    private String headline;
    private String about;
    private String skills;
    private String education;
    private String location;
}
