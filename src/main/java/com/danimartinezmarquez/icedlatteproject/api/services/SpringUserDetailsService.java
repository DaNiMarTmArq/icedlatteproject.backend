package com.danimartinezmarquez.icedlatteproject.api.services;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class SpringUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel == null) throw new UsernameNotFoundException("User with email " + email + " not found.");

        return new User(userModel.getEmail(), userModel.getHashedPassword(), Collections.emptyList());
    }
}
