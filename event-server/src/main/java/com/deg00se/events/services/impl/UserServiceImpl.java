package com.deg00se.events.services.impl;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.repositories.UserRepository;
import com.deg00se.events.services.EventService;
import com.deg00se.events.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventService eventService;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with that email not found"));
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with that id not found"));
    }

    @Override
    public User updateUserName(String email, String name) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User was not found"));

        user.setName(name);

        return userRepository.save(user);
    }

    @Override
    public List<Event> getUserEvents(UUID userId, String role) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(("User was not found"));
        }

        if (role != null && !role.isBlank()) {
            List<Event> events = switch (role) {
                case "creator" -> eventService.getUserCreatedEvents(userId);
                case "participant" -> eventService.getUserParticipatingEvent(userId);
                default -> List.of();
            };

            return events;
        }

        return List.of();
    }
}
