package com.deg00se.events.domain.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateEventRequest(
        String title,
        String description,
        String address,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer allowedCount,
        Set<UUID> tagIds
) { }
