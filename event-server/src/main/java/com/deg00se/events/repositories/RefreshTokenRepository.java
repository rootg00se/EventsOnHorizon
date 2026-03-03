package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.RefreshToken;
import com.deg00se.events.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByUser(User token);
    Optional<RefreshToken> findByToken(String token);
    boolean existsByToken(String token);
    void deleteByToken(String token);
}
