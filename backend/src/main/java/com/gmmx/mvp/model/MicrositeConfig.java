package com.gmmx.mvp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "microsite_configs", indexes = {
        @Index(name = "idx_microsite_tenant_id", columnList = "tenantId")
})
public class MicrositeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private String themePrimary = "#FF5C73";

    @Column(nullable = false)
    private String aboutText = "Welcome to our gym.";

    @Column(nullable = false)
    private String contactPhone = "+91";
}
