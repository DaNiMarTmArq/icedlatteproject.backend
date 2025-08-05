package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserDto;
import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.UserRepository;
import com.danimartinezmarquez.icedlatteproject.api.services.UserService;
import lombok.AllArgsConstructor;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/health")
@AllArgsConstructor
public class HealthController {

    private final UserService userService;
    private final UserRepository userRepository;

    @RequestMapping("/ok")
    public String ok() {
        return "ok";
    }

    @RequestMapping("/me")
    public ResponseEntity<UserDto> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

       Integer userId = Integer.valueOf((String) authentication.getPrincipal());

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserDto dto = new UserDto(
                user.getUserId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );

        return ResponseEntity.ok(dto);
    }
}
