package com.deg00se.events.controllers;

import com.deg00se.events.domain.dtos.CreateEventDto;
import com.deg00se.events.domain.dtos.EventResponse;
import com.deg00se.events.domain.dtos.UpdateEventDto;
import com.deg00se.events.domain.dtos.UpdateEventStatusDto;
import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.mappers.EventMapper;
import com.deg00se.events.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateEventDto createEventDto
    ) {
        Event event = eventService.createEvent(
                userDetails.getUsername(),
                eventMapper.toCreateRequest(createEventDto)
        );

        EventResponse eventResponse = eventMapper.toResponse(event);

        return new ResponseEntity<>(eventResponse, HttpStatus.CREATED);
    }

    @PostMapping("{id}/subscribe")
    public ResponseEntity<EventResponse> subscribeToEvent(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID id
    ) {
        Event event = eventService.subscribeToEvent(userDetails.getUsername(), id);
        EventResponse eventResponse = eventMapper.toResponse(event);

        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventResponse> eventResponses = events.stream().map(eventMapper::toResponse).toList();

        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID id) {
        Event event = eventService.getEventById(id);
        EventResponse eventResponse = eventMapper.toResponse(event);

        return ResponseEntity.ok(eventResponse);
    }

    @PatchMapping("{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEventDto updateEventDto
    ) {
        Event event = eventService.updateEvent(id, eventMapper.toUpdateRequest(updateEventDto));
        EventResponse eventResponse = eventMapper.toResponse(event);

        return ResponseEntity.ok(eventResponse);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<EventResponse> updateEventStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEventStatusDto updateEventStatusDto
    ) {
        Event event = eventService.updateEventStatus(id, updateEventStatusDto.status());
        EventResponse eventResponse = eventMapper.toResponse(event);

        return ResponseEntity.ok(eventResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/unsubscribe")
    public ResponseEntity<EventResponse> unsubscribeFromEvent(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID id
    ) {
        Event event = eventService.unsubscribeFromEvent(userDetails.getUsername(), id);
        EventResponse eventResponse = eventMapper.toResponse(event);

        return ResponseEntity.ok(eventResponse);
    }
}
