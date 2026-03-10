package com.deg00se.events.mappers;

import com.deg00se.events.domain.dtos.*;
import com.deg00se.events.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    EventResponse toResponse(Event event);
    UpdateEventRequest toUpdateRequest(UpdateEventDto updateEventDto);
    CreateEventRequest toCreateRequest(CreateEventDto createEventDto);
}