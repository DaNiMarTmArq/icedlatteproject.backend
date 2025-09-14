package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.JwtResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserCreationResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserLoginRequest;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserRegistrationRequest;
import com.danimartinezmarquez.icedlatteproject.api.services.JwtService;
import com.danimartinezmarquez.icedlatteproject.api.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final String REFRESH_COOKIE_NAME = "refresh_token";
    private final Duration REFRESH_COOKIE_TTL = Duration.ofDays(14);

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshAccessToken(HttpServletRequest request) {

        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (REFRESH_COOKIE_NAME.equals(c.getName())) {
                    refreshToken = c.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = jwtService.getClaimFromToken(refreshToken, "email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var newAccessToken = jwtService.generateAccessToken(user);
        return ResponseEntity.ok(new JwtResponse(newAccessToken));
    }

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<UserCreationResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserCreationResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login an existing user using Spring Auth Manager
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginRequest request) {
        //LoginResponse response = userService.login(request);
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authManager.authenticate(authToken);

        var user = userService.getUserByEmail(request.getEmail());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateAccessToken(user);



        ResponseCookie refreshCookie = ResponseCookie.from(REFRESH_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(REFRESH_COOKIE_TTL)
                .sameSite("None")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new JwtResponse(accessToken));
    }

}
