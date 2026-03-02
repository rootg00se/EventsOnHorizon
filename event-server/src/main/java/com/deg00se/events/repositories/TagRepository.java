package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> { }
