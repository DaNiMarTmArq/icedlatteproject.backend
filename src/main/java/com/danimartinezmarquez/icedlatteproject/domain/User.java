package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private UserRole role;
    private LocalDateTime dateJoined;
    private LocalDateTime dateUpdated;
    private String profileImageUrl;
}
