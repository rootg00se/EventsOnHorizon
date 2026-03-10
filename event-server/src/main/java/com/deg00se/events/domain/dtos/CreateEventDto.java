package com.deg00se.events.domain.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateEventDto(
        @NotBlank(message = "Title can't be null")
        @Size(min = 2, max = 128, message = "Title length should be between {min} and {max}")
        String title,

        @NotBlank(message = "Description can't be empty")
        @Size(min = 10, message = "Description should be at least {min} symbols long")
        String description,

        @NotBlank(message = "Address can't be null")
        @Size(min = 10, message = "Address should be at least {min} symbols")
        String address,

        @NotNull(message = "Start date can't be null")
        @Future(message = "Start day must be in future")
        LocalDateTime startDate,

        @NotNull(message = "End date can't be null")
        @Future(message = "End date must be in future")
        LocalDateTime endDate,

        @NotNull(message = "Allowed count can't be empty")
        Integer allowedCount,

        @NotEmpty(message = "Event must have at least 1 tag")
        Set<UUID> tagIds
) { }
