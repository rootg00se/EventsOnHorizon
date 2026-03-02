package com.deg00se.events.services.impl;

import com.deg00se.events.config.JwtConfig;
import com.deg00se.events.domain.entities.RefreshToken;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.repositories.RefreshTokenRepository;
import com.deg00se.events.services.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtConfig jwtConfig;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(
                userDetails.getUsername(),
                jwtConfig.getAccessExpiration(),
                jwtConfig.getAccessSecret()
        );
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(
                userDetails.getUsername(),
                jwtConfig.getRefreshExpiration(),
                jwtConfig.getRefreshSecret()
        );
    }

    @Override
    public void saveRefreshToken(String token, User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public UserDetails validateAccessToken(String token) {
        String username = Jwts.parserBuilder()
                .setSigningKey(getSignedKey(jwtConfig.getAccessSecret()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userDetailsService.loadUserByUsername(username);
    }

    private String generateToken(String email, long expiryTime, String secret) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSignedKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignedKey(String secret) {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
