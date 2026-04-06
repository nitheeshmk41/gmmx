package com.gmmx.mvp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "daily_qr", uniqueConstraints = {
        @UniqueConstraint(name = "uk_daily_qr_tenant_day", columnNames = {"tenantId", "qrDate"})
})
public class DailyQr {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private LocalDate qrDate;

    @Column(nullable = false)
    private String qrToken;
}
