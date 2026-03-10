package com.deg00se.events.services.impl;

import com.deg00se.events.domain.entities.Tag;
import com.deg00se.events.repositories.TagRepository;
import com.deg00se.events.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getAllTagsByIds(Set<UUID> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);

        if (tags.size() != tagIds.size()) {
            throw new EntityNotFoundException("Not all tags were found");
        }

        return tags;
    }
}
