package com.deg00se.events.mappers;

import com.deg00se.events.domain.dtos.UserResponse;
import com.deg00se.events.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toResponse(User user);
}
