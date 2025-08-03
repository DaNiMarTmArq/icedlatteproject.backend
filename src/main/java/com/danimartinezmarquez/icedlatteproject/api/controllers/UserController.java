package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.user.LoginResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserCreationResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserLoginRequest;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserRegistrationRequest;
import com.danimartinezmarquez.icedlatteproject.api.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<UserCreationResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserCreationResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login an existing user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        //LoginResponse response = userService.login(request);
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authManager.authenticate(authToken);
        return ResponseEntity.ok().build();
    }

}
