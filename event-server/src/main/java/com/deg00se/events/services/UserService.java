package com.deg00se.events.services;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(UUID userId);
    User updateUserName(String email, String name);
    List<Event> getUserEvents(UUID userId, String role);
}
