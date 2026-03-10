package com.deg00se.events.domain.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateEventDto(
        @NotBlank(message = "Address can't be empty")
        @Size(min = 10, message = "Address should be at least {min} symbols")
        String address,

        @NotNull(message = "Allowed count is required")
        @Min(value = 1, message = "Allowed count must be at least {value}")
        Integer allowedCount,

        @NotBlank(message = "Description can't be empty")
        @Size(min = 10, message = "Description should be at least {min} symbols")
        String description,

        @NotBlank(message = "Title can't be empty")
        @Size(min = 2, max = 128, message = "Title length should be between {min} and {max}")
        String title
) { }
