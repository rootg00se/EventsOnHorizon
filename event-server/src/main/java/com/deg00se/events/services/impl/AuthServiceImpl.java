package com.deg00se.events.services.impl;

import com.deg00se.events.config.JwtConfig;
import com.deg00se.events.domain.dtos.AuthResult;
import com.deg00se.events.domain.entities.RefreshToken;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.repositories.UserRepository;
import com.deg00se.events.services.AuthService;
import com.deg00se.events.services.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtConfig jwtConfig;

    @Override
    @Transactional
    public AuthResult register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with that email already exists");
        }

        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);

        return authenticate(user, password);
    }

    @Override
    @Transactional
    public AuthResult login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect email or password"));

        boolean isPasswordsMatching = passwordEncoder.matches(password, user.getPasswordHash());
        if (!isPasswordsMatching) throw new IllegalArgumentException("Incorrect email or password");

        return authenticate(user, password);
    }

    @Override
    @Transactional
    public AuthResult refresh(String refreshToken) {
        log.warn(refreshToken);
        if (refreshToken == null || refreshToken.isBlank()) throw new EntityNotFoundException("Token not found");

        UserDetails userDetails = tokenService.validateRefreshToken(refreshToken);
        RefreshToken tokenFromDb = tokenService.findToken(refreshToken);
        User user = tokenFromDb.getUser();

        tokenService.deleteRefreshToken(tokenFromDb.getToken());

        String accessToken = tokenService.generateAccessToken(userDetails);
        String newRefreshToken = tokenService.generateRefreshToken(userDetails);

        tokenService.saveRefreshToken(newRefreshToken, user);

        return new AuthResult(accessToken, newRefreshToken, jwtConfig.getAccessExpiration());
    }

    private AuthResult authenticate(User user, String rawPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), rawPassword)
        );

        String accessToken = tokenService.generateAccessToken((UserDetails) authentication.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal());

        tokenService.saveRefreshToken(refreshToken, user);

        return new AuthResult(accessToken, refreshToken, jwtConfig.getAccessExpiration());
    }
}