package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> { }
