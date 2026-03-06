package com.deg00se.events.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserNameDto(
        @NotBlank(message = "Name can't be blank")
        @Size(min = 2, max = 128, message = "Name length should be between {min} and {max} symbols")
        String name
) { }
