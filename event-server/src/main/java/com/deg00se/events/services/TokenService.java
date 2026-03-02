package com.deg00se.events.services;

import com.deg00se.events.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    void saveRefreshToken(String token, User user);
    UserDetails validateAccessToken(String token);
}
