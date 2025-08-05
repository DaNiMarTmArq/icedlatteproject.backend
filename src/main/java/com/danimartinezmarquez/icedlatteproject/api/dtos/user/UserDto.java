package com.danimartinezmarquez.icedlatteproject.api.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    String userId;
    String firstName;
    String lastName;
    String email;
}
