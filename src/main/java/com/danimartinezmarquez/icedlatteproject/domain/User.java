package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String profileImageUrl;
}
