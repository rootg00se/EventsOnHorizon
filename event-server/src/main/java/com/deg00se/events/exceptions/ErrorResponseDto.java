package com.deg00se.events.exceptions;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String detailedMessage,
        LocalDateTime errorTime,
        int status
) { }
