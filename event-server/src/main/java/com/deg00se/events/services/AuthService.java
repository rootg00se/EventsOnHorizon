package com.deg00se.events.services;

import com.deg00se.events.domain.dtos.AuthResult;

public interface AuthService {
    AuthResult register(String email, String password);
    AuthResult login(String email, String password);
    AuthResult refresh(String refreshToken);
}
