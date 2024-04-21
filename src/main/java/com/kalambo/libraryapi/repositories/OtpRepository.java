package com.kalambo.libraryapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.Otp;
import java.util.Optional;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.OtpTypeEnum;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {
    Optional<Otp> findByCode(Integer code);

    Optional<Otp> findByUserAndType(User user, OtpTypeEnum type);

    boolean existsByUserAndType(User user, OtpTypeEnum type);
}
