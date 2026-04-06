package com.gmmx.mvp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class UserDtos {

    public record CreateUserRequest(
            @NotBlank String tenantSlug,
            @NotBlank String fullName,
            @NotBlank @Pattern(regexp = "^[0-9]{10}$") String mobile,
            String email
    ) {}

    public record UserResponse(
            UUID id,
            String fullName,
            String mobile,
            String email,
            String role
    ) {}
}
