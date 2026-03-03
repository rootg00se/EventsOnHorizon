package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationLink(String activationLink);
    boolean existsByEmail(String email);
}
