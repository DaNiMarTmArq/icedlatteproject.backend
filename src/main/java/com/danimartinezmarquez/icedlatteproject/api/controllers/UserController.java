package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.user.LoginResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserCreationResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserLoginRequest;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserRegistrationRequest;
import com.danimartinezmarquez.icedlatteproject.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<UserCreationResponse> register(@RequestBody UserRegistrationRequest request) {
        UserCreationResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login an existing user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
