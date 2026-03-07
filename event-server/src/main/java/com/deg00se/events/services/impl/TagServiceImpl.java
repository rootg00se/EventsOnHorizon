package com.deg00se.events.services.impl;

import com.deg00se.events.domain.entities.Tag;
import com.deg00se.events.repositories.TagRepository;
import com.deg00se.events.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
