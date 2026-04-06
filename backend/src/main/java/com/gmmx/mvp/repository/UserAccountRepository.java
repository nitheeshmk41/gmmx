package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.UserAccount;
import com.gmmx.mvp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    long countByTenantIdAndRoleAndActiveTrue(UUID tenantId, UserRole role);

    Optional<UserAccount> findByTenantIdAndMobileAndRoleAndActiveTrue(UUID tenantId, String mobile, UserRole role);

    List<UserAccount> findByTenantIdAndMobileAndActiveTrue(UUID tenantId, String mobile);

    Optional<UserAccount> findByTenantIdAndIdAndRole(UUID tenantId, UUID id, UserRole role);
}
