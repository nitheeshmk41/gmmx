package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.MicrositeConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MicrositeConfigRepository extends JpaRepository<MicrositeConfig, UUID> {
    Optional<MicrositeConfig> findByTenantId(UUID tenantId);
}
