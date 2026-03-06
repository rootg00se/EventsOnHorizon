package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findAllByCreator_UserId(UUID userId);
    List<Event> findAllByParticipants_UserId(UUID useId);
}
