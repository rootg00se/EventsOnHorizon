package com.deg00se.events.domain.dtos;

public record TokenResponse(
    String accessToken,
    long expiresIn
) { }
