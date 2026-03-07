package com.deg00se.events.controllers;

import com.deg00se.events.domain.dtos.EventResponse;
import com.deg00se.events.domain.dtos.UpdateUserNameDto;
import com.deg00se.events.domain.dtos.UserResponse;
import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.mappers.EventMapper;
import com.deg00se.events.mappers.UserMapper;
import com.deg00se.events.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @GetMapping("me")
    public ResponseEntity<UserResponse> getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getAuthenticatedUser(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("{id}/events")
    public ResponseEntity<List<EventResponse>> getUserEvents(
            @RequestParam(required = false) String role,
            @PathVariable UUID id
    ) {
        List<Event> events = userService.getUserEvents(id, role);
        List<EventResponse> eventsResponse = events.stream().map(eventMapper::toResponse).toList();

        return ResponseEntity.ok(eventsResponse);
    }

    @PatchMapping("me/name")
    public ResponseEntity<UserResponse> updateUserName(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateUserNameDto updateUserNameDto
    ) {
        User user = userService.updateUserName(userDetails.getUsername(), updateUserNameDto.name());
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("me/avatar")
    public ResponseEntity<UserResponse> updateUserAvatar(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        User user = userService.updateUserAvatar(userDetails.getUsername(), file);
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("me/avatar")
    public ResponseEntity<UserResponse> deleteUserAvatar(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        User user = userService.deleteUserAvatar(userDetails.getUsername());
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }
}
