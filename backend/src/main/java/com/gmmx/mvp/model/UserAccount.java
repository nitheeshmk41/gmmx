package com.gmmx.mvp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_accounts", indexes = {
        @Index(name = "idx_user_accounts_tenant_id", columnList = "tenantId"),
        @Index(name = "idx_user_accounts_mobile", columnList = "mobile")
})
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String mobile;

    private String email;

    private String passwordHash;

    @Column(nullable = false)
    private boolean active = true;
}
