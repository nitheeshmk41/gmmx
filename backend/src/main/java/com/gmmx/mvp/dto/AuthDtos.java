package com.gmmx.mvp.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthDtos {

    public record OwnerRegisterRequest(
            @NotBlank String ownerName,
            @NotBlank @Pattern(regexp = "^[0-9]{10}$") String mobile,
            @NotBlank @Email String email,
            @NotBlank String gymName,
            @NotBlank String location,
            @NotBlank @Pattern(regexp = "^[a-z0-9-]{3,30}$") String slug,
            @NotBlank @Size(min = 6, max = 50) String password
    ) {}

    public record OwnerRegisterResponse(
            UUID tenantId,
            String slug,
            UUID ownerId,
            LocalDateTime trialEndsAt
    ) {}

    public record OtpRequest(
            @NotBlank String tenantSlug,
            @NotBlank @Pattern(regexp = "^[0-9]{10}$") String mobile
    ) {}

    public record OtpVerifyRequest(
            @NotBlank String tenantSlug,
            @NotBlank @Pattern(regexp = "^[0-9]{10}$") String mobile,
            @NotBlank @Pattern(regexp = "^[0-9]{6}$") String code
    ) {}

    public record OtpResponse(
            String message,
            Integer expiresInSeconds,
            String debugCode
    ) {}

    public record OtpVerifyResponse(
            UUID userId,
            String role,
            UUID tenantId,
            String tenantSlug
    ) {}
}
