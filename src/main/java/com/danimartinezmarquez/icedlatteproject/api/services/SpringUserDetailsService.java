package com.danimartinezmarquez.icedlatteproject.api.services;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpringUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel == null) throw new UsernameNotFoundException("User with email " + email + " not found.");
        List<String> roles = List.of(userModel.getRole().name());
        Set<SimpleGrantedAuthority> authorities =
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toSet());
        return new User(userModel.getEmail(), userModel.getHashedPassword(), authorities);
    }
}
