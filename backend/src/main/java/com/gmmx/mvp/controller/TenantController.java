package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.TenantDtos;
import com.gmmx.mvp.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/tenant/create")
    public TenantDtos.TenantCreateResponse createTenant(@Valid @RequestBody TenantDtos.TenantCreateRequest request) {
        return tenantService.create(request);
    }

    @GetMapping("/public/{slug}")
    public TenantDtos.PublicGymResponse getPublicGym(@PathVariable String slug) {
        return tenantService.getPublicBySlug(slug);
    }
}
