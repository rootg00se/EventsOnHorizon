package com.deg00se.events.services.impl;

import com.deg00se.events.domain.dtos.CreateEventRequest;
import com.deg00se.events.domain.dtos.UpdateEventRequest;
import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.Tag;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.domain.enums.EventStatus;
import com.deg00se.events.repositories.EventRepository;
import com.deg00se.events.services.EventService;
import com.deg00se.events.services.TagService;
import com.deg00se.events.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final TagService tagService;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event was not found"));
    }

    @Override
    @Transactional
    public Event updateEventStatus(UUID eventId, EventStatus status) {
        Event event = getEventById(eventId);
        event.setStatus(status);

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event updateEvent(UUID eventId, UpdateEventRequest updateEventRequest) {
        Event event = getEventById(eventId);

        event.setAddress(updateEventRequest.address());
        event.setAllowedCount(updateEventRequest.allowedCount());
        event.setTitle(updateEventRequest.title());
        event.setDescription(updateEventRequest.description());

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event createEvent(String email, CreateEventRequest createEventRequest) {
        Event event = new Event();
        User user = userService.getUserByEmail(email);

        event.setTitle(createEventRequest.title());
        event.setDescription(createEventRequest.description());
        event.setAddress(createEventRequest.address());
        event.setStartDate(createEventRequest.startDate());
        event.setEndDate(createEventRequest.endDate());
        event.setAllowedCount(createEventRequest.allowedCount());
        event.setCreator(user);

        Set<UUID> tagIds = createEventRequest.tagIds();
        List<Tag> tags = tagService.getAllTagsByIds(tagIds);

        event.setTags(new HashSet<>(tags));

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event subscribeToEvent(String email, UUID eventId) {
        Event event = getEventById(eventId);
        User user = userService.getUserByEmail(email);

        event.addParticipant(user);

        return eventRepository.save(event);
    }

    @Override
    public Event unsubscribeFromEvent(String email, UUID eventId) {
        Event event = getEventById(eventId);
        User user = userService.getUserByEmail(email);

        event.removeParticipant(user);

        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        Event event = getEventById(eventId);
        eventRepository.delete(event);
    }
}
