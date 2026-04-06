package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GymRepository extends JpaRepository<Gym, UUID> {
    Optional<Gym> findByTenantId(UUID tenantId);
}
