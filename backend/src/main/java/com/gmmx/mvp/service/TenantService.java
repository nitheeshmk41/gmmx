package com.gmmx.mvp.service;

import com.gmmx.mvp.dto.TenantDtos;
import com.gmmx.mvp.model.Gym;
import com.gmmx.mvp.model.MicrositeConfig;
import com.gmmx.mvp.model.Subscription;
import com.gmmx.mvp.model.Tenant;
import com.gmmx.mvp.repository.GymRepository;
import com.gmmx.mvp.repository.MicrositeConfigRepository;
import com.gmmx.mvp.repository.SubscriptionRepository;
import com.gmmx.mvp.repository.TenantRepository;
import com.gmmx.mvp.util.SlugUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final GymRepository gymRepository;
    private final MicrositeConfigRepository micrositeConfigRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TenantResolver tenantResolver;

    public TenantService(TenantRepository tenantRepository, GymRepository gymRepository, MicrositeConfigRepository micrositeConfigRepository, SubscriptionRepository subscriptionRepository, TenantResolver tenantResolver) {
        this.tenantRepository = tenantRepository;
        this.gymRepository = gymRepository;
        this.micrositeConfigRepository = micrositeConfigRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.tenantResolver = tenantResolver;
    }

    @Transactional
    public TenantDtos.TenantCreateResponse create(TenantDtos.TenantCreateRequest request) {
        String slug = SlugUtil.normalize(request.slug());
        if (tenantRepository.existsBySlug(slug)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slug already in use");
        }

        Tenant tenant = new Tenant();
        tenant.setSlug(slug);
        tenant.setGymName(request.gymName());
        tenant.setLocation(request.location());
        tenant.setTrialStartAt(LocalDateTime.now());
        tenant.setTrialEndAt(LocalDateTime.now().plusDays(14));
        tenant.setMemberLimit(10);
        tenant.setTrainerLimit(2);
        tenantRepository.save(tenant);

        Gym gym = new Gym();
        gym.setTenantId(tenant.getId());
        gym.setName(request.gymName());
        gym.setLocation(request.location());
        gym.setContactEmail("owner@" + slug + ".gmmx.app");
        gymRepository.save(gym);

        Subscription subscription = new Subscription();
        subscription.setTenantId(tenant.getId());
        subscription.setPlanName("TRIAL");
        subscription.setStartAt(tenant.getTrialStartAt());
        subscription.setEndAt(tenant.getTrialEndAt());
        subscription.setMemberLimit(10);
        subscription.setTrainerLimit(2);
        subscriptionRepository.save(subscription);

        MicrositeConfig config = new MicrositeConfig();
        config.setTenantId(tenant.getId());
        micrositeConfigRepository.save(config);

        return new TenantDtos.TenantCreateResponse(tenant.getId(), tenant.getSlug());
    }

    public TenantDtos.PublicGymResponse getPublicBySlug(String slug) {
        Tenant tenant = tenantResolver.bySlugOrThrow(slug);
        Gym gym = gymRepository.findByTenantId(tenant.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found"));
        MicrositeConfig config = micrositeConfigRepository.findByTenantId(tenant.getId())
                .orElseGet(() -> {
                    MicrositeConfig fallback = new MicrositeConfig();
                    fallback.setTenantId(tenant.getId());
                    return micrositeConfigRepository.save(fallback);
                });

        return new TenantDtos.PublicGymResponse(
                tenant.getSlug(),
                gym.getName(),
                gym.getLocation(),
                config.getAboutText(),
                config.getContactPhone(),
                config.getThemePrimary()
        );
    }
}
