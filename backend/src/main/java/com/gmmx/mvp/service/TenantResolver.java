package com.gmmx.mvp.service;

import com.gmmx.mvp.model.Tenant;
import com.gmmx.mvp.repository.TenantRepository;
import com.gmmx.mvp.util.SlugUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TenantResolver {

    private final TenantRepository tenantRepository;

    public TenantResolver(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant bySlugOrThrow(String rawSlug) {
        String slug = SlugUtil.normalize(rawSlug);
        return tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));
    }
}
