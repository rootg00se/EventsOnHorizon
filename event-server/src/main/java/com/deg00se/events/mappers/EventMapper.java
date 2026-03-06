package com.deg00se.events.mappers;

import com.deg00se.events.domain.dtos.EventResponse;
import com.deg00se.events.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    EventResponse toResponse(Event event);
}