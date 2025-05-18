package com.example.socialapp.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;
    private String about;
    private String skills;
    private String education;
    private String location;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_userprofile_user"))
    private User user;
}
