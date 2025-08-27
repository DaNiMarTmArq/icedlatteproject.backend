package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String profileImageUrl;
}
