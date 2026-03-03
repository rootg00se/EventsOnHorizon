package com.deg00se.events.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email(
                message = "Email is not valid",
                regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+$"
        )
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotBlank(message = "Password can't be empty")
        @Size(min = 8, max = 32, message = "Password size must be between {min} and {max}")
        String password
) { }
