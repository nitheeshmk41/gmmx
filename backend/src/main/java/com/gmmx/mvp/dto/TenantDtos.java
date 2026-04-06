package com.gmmx.mvp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class TenantDtos {

    public record TenantCreateRequest(
            @NotBlank String gymName,
            @NotBlank String location,
            @NotBlank @Pattern(regexp = "^[a-z0-9-]{3,30}$") String slug
    ) {}

    public record TenantCreateResponse(
            UUID tenantId,
            String slug
    ) {}

    public record PublicGymResponse(
            String slug,
            String gymName,
            String location,
            String about,
            String contactPhone,
            String themePrimary
    ) {}
}
