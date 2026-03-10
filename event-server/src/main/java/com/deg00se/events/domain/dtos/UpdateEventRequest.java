package com.deg00se.events.domain.dtos;

public record UpdateEventRequest(
        String address,
        Integer allowedCount,
        String description,
        String title
) { }
