package com.deg00se.events.services.impl;

import com.deg00se.events.config.JwtConfig;
import com.deg00se.events.domain.dtos.AuthResult;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.repositories.UserRepository;
import com.deg00se.events.services.AuthService;
import com.deg00se.events.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtConfig jwtConfig;

    @Override
    public AuthResult register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with that email already exists");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String accessToken = tokenService.generateAccessToken((UserDetails) authentication.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal());

        tokenService.saveRefreshToken(refreshToken, user);

        return new AuthResult(accessToken, refreshToken, jwtConfig.getAccessExpiration());
    }
}