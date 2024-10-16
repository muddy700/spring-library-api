package com.kalambo.libraryapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, UUID> {
}
