package com.deg00se.events.services;

import com.deg00se.events.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> getAllTagsByIds(Set<UUID> tagIds);
}
