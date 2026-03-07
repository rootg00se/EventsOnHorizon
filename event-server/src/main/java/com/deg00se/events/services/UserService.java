package com.deg00se.events.services;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(UUID userId);
    User updateUserName(String email, String name);
    User updateUserAvatar(String email, MultipartFile file) throws IOException;
    User deleteUserAvatar(String email) throws IOException;
    List<Event> getUserEvents(UUID userId, String role);
}
