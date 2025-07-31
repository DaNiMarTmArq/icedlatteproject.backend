package com.danimartinezmarquez.icedlatteproject.api.dtos.user;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserModel.Role role;
}
