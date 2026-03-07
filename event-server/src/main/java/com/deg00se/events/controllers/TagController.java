package com.deg00se.events.controllers;

import com.deg00se.events.domain.dtos.TagResponse;
import com.deg00se.events.domain.entities.Tag;
import com.deg00se.events.mappers.TagMapper;
import com.deg00se.events.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toResponse).toList();

        return ResponseEntity.ok(tagResponses);
    }
}