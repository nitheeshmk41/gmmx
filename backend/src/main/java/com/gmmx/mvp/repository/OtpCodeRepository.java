package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    Optional<OtpCode> findTopByTenantIdAndMobileAndUsedFalseOrderByExpiresAtDesc(UUID tenantId, String mobile);
}
