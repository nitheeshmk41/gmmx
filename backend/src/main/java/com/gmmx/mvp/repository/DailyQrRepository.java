package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.DailyQr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface DailyQrRepository extends JpaRepository<DailyQr, UUID> {
    Optional<DailyQr> findByTenantIdAndQrDate(UUID tenantId, LocalDate qrDate);

    Optional<DailyQr> findByTenantIdAndQrToken(UUID tenantId, String qrToken);
}
