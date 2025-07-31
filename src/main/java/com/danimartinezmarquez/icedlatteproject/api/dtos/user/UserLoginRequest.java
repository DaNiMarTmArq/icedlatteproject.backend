package com.danimartinezmarquez.icedlatteproject.api.dtos.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    private String email;
    private String password;
}
