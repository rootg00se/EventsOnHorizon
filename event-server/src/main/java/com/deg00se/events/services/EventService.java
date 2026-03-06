package com.deg00se.events.services;

import com.deg00se.events.domain.entities.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<Event> getUserCreatedEvents(UUID userId);
    List<Event> getUserParticipatingEvent(UUID userId);
}
