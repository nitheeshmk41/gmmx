package com.gmmx.mvp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tenants", indexes = {
        @Index(name = "idx_tenants_slug", columnList = "slug", unique = true)
})
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String gymName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime trialStartAt;

    @Column(nullable = false)
    private LocalDateTime trialEndAt;

    @Column(nullable = false)
    private Integer memberLimit;

    @Column(nullable = false)
    private Integer trainerLimit;

    @Column(nullable = false)
    private boolean qrEnabled = true;

    @Column(nullable = false)
    private boolean active = true;
}
