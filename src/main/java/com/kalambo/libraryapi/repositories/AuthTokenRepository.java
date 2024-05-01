package com.kalambo.libraryapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.AuthToken;
import java.util.Optional;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.TokenTypeEnum;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByUserAndType(User user, TokenTypeEnum type);

    boolean existsByUserAndType(User user, TokenTypeEnum type);
}
