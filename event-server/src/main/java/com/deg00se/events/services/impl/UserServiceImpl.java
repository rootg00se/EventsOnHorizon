package com.deg00se.events.services.impl;

import com.deg00se.events.domain.entities.Event;
import com.deg00se.events.domain.entities.User;
import com.deg00se.events.domain.enums.StorageType;
import com.deg00se.events.repositories.EventRepository;
import com.deg00se.events.repositories.UserRepository;
import com.deg00se.events.services.FileService;
import com.deg00se.events.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FileService fileService;

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
        User user = getUserByEmail(email);
        user.setName(name);

        return userRepository.save(user);
    }

    @Override
    public User updateUserAvatar(String email, MultipartFile file) throws IOException {
        User user = getUserByEmail(email);
        fileService.validateFile(file);
        String storagePath;

        try (InputStream inputStream = file.getInputStream()) {
            storagePath = fileService.uploadFile(inputStream, file.getOriginalFilename(), StorageType.AVATAR);
        }

        user.setAvatarKey(storagePath);

        return userRepository.save(user);
    }

    @Override
    public User deleteUserAvatar(String email) throws IOException {
        User user = getUserByEmail(email);

        if (user.getAvatarKey() != null) {
            fileService.deleteFile(user.getAvatarKey());
            user.setAvatarKey(null);
        }

        return userRepository.save(user);
    }

    @Override
    public List<Event> getUserEvents(UUID userId, String role) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(("User was not found"));
        }

        if (role != null && !role.isBlank()) {
            List<Event> events = switch (role) {
                case "creator" -> eventRepository.findAllByCreator_UserId(userId);
                case "participant" -> eventRepository.findAllByParticipants_UserId(userId);
                default -> List.of();
            };

            return events;
        }

        return List.of();
    }
}
