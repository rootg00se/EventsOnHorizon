package com.deg00se.events.domain.dtos;

import com.deg00se.events.domain.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EventResponse(
        UUID eventId,

        String title,
        String description,
        String bannerKey,
        String address,

        LocalDateTime startDate,
        LocalDateTime endDate,
        EventStatus status,
        Integer allowedCount,

        CreatorDto creator,
        Set<TagResponse> tags,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
