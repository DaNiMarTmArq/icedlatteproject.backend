package com.danimartinezmarquez.icedlatteproject.api.services;

import com.danimartinezmarquez.icedlatteproject.api.dtos.user.LoginResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserCreationResponse;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserLoginRequest;
import com.danimartinezmarquez.icedlatteproject.api.dtos.user.UserRegistrationRequest;
import com.danimartinezmarquez.icedlatteproject.api.exceptions.*;
import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Provided by Spring Security

    public UserCreationResponse register(UserRegistrationRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        }

        // Create new user
        UserModel newUser = UserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .hashedPassword(passwordEncoder.encode(request.getPassword()))
                .role("admin".equalsIgnoreCase(request.getRole())
                        ? UserModel.Role.admin
                        : UserModel.Role.user)
                .dateJoined(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .build();

        UserModel savedUser = userRepository.save(newUser);

        return UserCreationResponse.builder()
                .userId(savedUser.getUserId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    public LoginResponse login(UserLoginRequest request) {
        UserModel existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser == null) {
            throw new UserNotFoundException("User with email " + request.getEmail() + " not found.");
        }

        if (!passwordEncoder.matches(request.getPassword(), existingUser.getHashedPassword())) {
            throw new UserInvalidCredentialsException("Invalid credentials provided.");
        }

        return LoginResponse.builder()
                .userId(existingUser.getUserId())
                .firstName(existingUser.getFirstName())
                .lastName(existingUser.getLastName())
                .email(existingUser.getEmail())
                .role(existingUser.getRole())
                .build();
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}