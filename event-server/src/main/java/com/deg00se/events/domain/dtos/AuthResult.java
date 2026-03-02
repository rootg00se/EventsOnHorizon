package com.deg00se.events.domain.dtos;

public record AuthResult(
    String accessToken,
    String refreshToken,
    long accessExpiresIn
) { }
