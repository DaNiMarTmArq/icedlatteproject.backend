package com.danimartinezmarquez.icedlatteproject.api.services;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;
    private static final Duration ACCESS_TOKEN_TTL  = Duration.ofMinutes(30);
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(14);

    public String generateAccessToken(UserModel user) {
        return createToken(user, ACCESS_TOKEN_TTL, "access");
    }

    public String generateRefreshToken(UserModel user) {
        return createToken(user, REFRESH_TOKEN_TTL, "refresh");
    }

    private String createToken(UserModel user, Duration ttl, String tokenType) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + ttl.toMillis());

        return Jwts
                .builder()
                .subject(user.getUserId().toString())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("tokenType", tokenType) // optional but handy
                .issuedAt(now)
                .expiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSubjectFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
      return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
