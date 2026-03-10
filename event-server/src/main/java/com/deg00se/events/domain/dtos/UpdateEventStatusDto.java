package com.deg00se.events.domain.dtos;

import com.deg00se.events.domain.enums.EventStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateEventStatusDto(
        @NotNull(message = "Event status is required")
        EventStatus status
) { }
