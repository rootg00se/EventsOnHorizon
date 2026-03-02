package com.deg00se.events.controllers;

import com.deg00se.events.domain.dtos.AuthResult;
import com.deg00se.events.domain.dtos.RegisterRequest;
import com.deg00se.events.domain.dtos.TokenResponse;
import com.deg00se.events.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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

    private void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(604800);
        cookie.setPath("/api/v1/auth/refresh");

        response.addCookie(cookie);
    }

/*    @PostMapping("login")
    public ResponseEntity<?> register() {}

    @PostMapping("logout")
    public ResponseEntity<?> register() {}

    @PostMapping("refresh")
    public ResponseEntity<?> register() {}

    @GetMapping("activate/{link}")
    public ResponseEntity<?> register() {}*/
}
