package com.danimartinezmarquez.icedlatteproject.api.filters;

import com.danimartinezmarquez.icedlatteproject.api.repositories.UserRepository;
import com.danimartinezmarquez.icedlatteproject.api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ", "");
        if (!jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

       var user = userRepository.findByEmail(jwtService.getClaimFromToken(token, "email"));

        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        List<String> roles = List.of(user.getRole().name());
        Set<SimpleGrantedAuthority> authorities =
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toSet());

        var authentication = new UsernamePasswordAuthenticationToken(
                jwtService.getSubjectFromToken(token),
                null,
                authorities
                );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
