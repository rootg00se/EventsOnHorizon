package com.deg00se.events.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        String email,
        String name,
        String avatarKey,
        Boolean isActivated,
        String activationLink,
        LocalDateTime createdAt
) { }
