package com.deg00se.events.domain.dtos;

import java.util.UUID;

public record CreatorDto(
        UUID userId,
        String name,
        String avatarKey
) { }
