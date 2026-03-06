package com.deg00se.events.domain.dtos;

import java.util.UUID;

public record TagResponse(
        UUID tagId,
        String name
) { }
