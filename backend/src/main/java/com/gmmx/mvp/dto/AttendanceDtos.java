package com.gmmx.mvp.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class AttendanceDtos {

    public record QrGenerateRequest(@NotBlank String tenantSlug) {}

    public record QrGenerateResponse(String tenantSlug, String qrToken, LocalDate qrDate) {}

    public record QrScanRequest(
            @NotBlank String tenantSlug,
            @NotBlank String qrToken,
            UUID memberId
    ) {}

    public record QrScanResponse(String message, LocalDate attendanceDate) {}
}
