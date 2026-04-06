package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findTopByTenantIdOrderByEndAtDesc(UUID tenantId);
}
