package com.deg00se.events.services;

import com.deg00se.events.domain.dtos.CreateEventRequest;
import com.deg00se.events.domain.dtos.UpdateEventRequest;
import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.enums.EventStatus;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<Event> getAllEvents();
    Event getEventById(UUID eventId);
    Event updateEventStatus(UUID eventId, EventStatus status);
    Event updateEvent(UUID eventId, UpdateEventRequest updateEventRequest);
    Event createEvent(String email, CreateEventRequest createEventRequest);
    Event subscribeToEvent(String email, UUID eventId);
    Event unsubscribeFromEvent(String email, UUID eventId);
    void deleteEvent(UUID eventId);
}
