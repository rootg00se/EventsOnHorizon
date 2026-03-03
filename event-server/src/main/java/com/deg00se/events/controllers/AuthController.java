package com.deg00se.events.controllers;

import com.deg00se.events.domain.dtos.AuthResult;
import com.deg00se.events.domain.dtos.LoginRequest;
import com.deg00se.events.domain.dtos.RegisterRequest;
import com.deg00se.events.domain.dtos.TokenResponse;
import com.deg00se.events.services.AuthService;
import com.deg00se.events.services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("register")
    public ResponseEntity<TokenResponse> register(
            HttpServletResponse response,
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        AuthResult authResult = authService.register(registerRequest.email(), registerRequest.password());
        TokenResponse tokenResponse = new TokenResponse(authResult.accessToken(), authResult.accessExpiresIn());

        addRefreshTokenToCookie(response, authResult.refreshToken());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(
            HttpServletResponse response,
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        AuthResult authResult = authService.login(loginRequest.email(), loginRequest.password());
        TokenResponse tokenResponse = new TokenResponse(authResult.accessToken(), authResult.accessExpiresIn());

        addRefreshTokenToCookie(response, authResult.refreshToken());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response,
            @CookieValue(name = "refreshToken", required = false) String token
    ) {
        tokenService.deleteRefreshToken(token);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(
            HttpServletResponse response,
            @CookieValue(name = "refreshToken", required = false) String token
    ) {
        AuthResult authResult = authService.refresh(token);
        TokenResponse tokenResponse = new TokenResponse(authResult.accessToken(), authResult.accessExpiresIn());

        addRefreshTokenToCookie(response, authResult.refreshToken());

        return ResponseEntity.ok(tokenResponse);
    }

    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(604800)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

/*
    @GetMapping("activate/{link}")
    public ResponseEntity<?> register() {}*/
}
