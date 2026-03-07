package com.deg00se.events.mappers;

import com.deg00se.events.domain.dtos.TagResponse;
import com.deg00se.events.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagResponse toResponse(Tag tag);
}
