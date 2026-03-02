package com.deg00se.events.repositories;

import com.deg00se.events.domain.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> { }
