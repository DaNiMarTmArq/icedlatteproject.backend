package com.danimartinezmarquez.icedlatteproject.api.dtos.user;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserModel.Role role;
}
