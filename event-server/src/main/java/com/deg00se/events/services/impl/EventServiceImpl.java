package com.deg00se.events.services.impl;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.repositories.EventRepository;
import com.deg00se.events.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> getUserCreatedEvents(UUID userId) {
        return eventRepository.findAllByCreator_UserId(userId);
    }

    @Override
    public List<Event> getUserParticipatingEvent(UUID userId) {
        return eventRepository.findAllByParticipants_UserId(userId);
    }
}
