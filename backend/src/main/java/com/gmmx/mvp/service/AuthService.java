package com.gmmx.mvp.service;

import com.gmmx.mvp.dto.AuthDtos;
import com.gmmx.mvp.model.*;
import com.gmmx.mvp.repository.*;
import com.gmmx.mvp.util.SlugUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AuthService {

    private final TenantRepository tenantRepository;
    private final GymRepository gymRepository;
    private final UserAccountRepository userAccountRepository;
    private final OtpCodeRepository otpCodeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MicrositeConfigRepository micrositeConfigRepository;
    private final TenantResolver tenantResolver;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Random random = new Random();

    @Value("${app.otp.expiry-minutes:5}")
    private int otpExpiryMinutes;

    public AuthService(
            TenantRepository tenantRepository,
            GymRepository gymRepository,
            UserAccountRepository userAccountRepository,
            OtpCodeRepository otpCodeRepository,
            SubscriptionRepository subscriptionRepository,
            MicrositeConfigRepository micrositeConfigRepository,
            TenantResolver tenantResolver
    ) {
        this.tenantRepository = tenantRepository;
        this.gymRepository = gymRepository;
        this.userAccountRepository = userAccountRepository;
        this.otpCodeRepository = otpCodeRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.micrositeConfigRepository = micrositeConfigRepository;
        this.tenantResolver = tenantResolver;
    }

    @Transactional
    public AuthDtos.OwnerRegisterResponse registerOwner(AuthDtos.OwnerRegisterRequest request) {
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
        gym.setContactEmail(request.email());
        gymRepository.save(gym);

        UserAccount owner = new UserAccount();
        owner.setTenantId(tenant.getId());
        owner.setRole(UserRole.OWNER);
        owner.setFullName(request.ownerName());
        owner.setMobile(request.mobile());
        owner.setEmail(request.email());
        owner.setPasswordHash(encoder.encode(request.password()));
        userAccountRepository.save(owner);

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

        return new AuthDtos.OwnerRegisterResponse(tenant.getId(), tenant.getSlug(), owner.getId(), tenant.getTrialEndAt());
    }

    @Transactional
    public AuthDtos.OtpResponse sendOtp(AuthDtos.OtpRequest request) {
        Tenant tenant = tenantResolver.bySlugOrThrow(request.tenantSlug());
        String code = String.format("%06d", random.nextInt(1_000_000));

        OtpCode otp = new OtpCode();
        otp.setTenantId(tenant.getId());
        otp.setMobile(request.mobile());
        otp.setCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
        otpCodeRepository.save(otp);

        return new AuthDtos.OtpResponse("OTP sent", otpExpiryMinutes * 60, code);
    }

    @Transactional
    public AuthDtos.OtpVerifyResponse verifyOtp(AuthDtos.OtpVerifyRequest request) {
        Tenant tenant = tenantResolver.bySlugOrThrow(request.tenantSlug());
        OtpCode otp = otpCodeRepository.findTopByTenantIdAndMobileAndUsedFalseOrderByExpiresAtDesc(tenant.getId(), request.mobile())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP not requested"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        if (!otp.getCode().equals(request.code())) {
            otp.setAttempts(otp.getAttempts() + 1);
            if (otp.getAttempts() >= 5) {
                otp.setUsed(true);
            }
            otpCodeRepository.save(otp);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        otp.setUsed(true);
        otpCodeRepository.save(otp);

        List<UserAccount> users = userAccountRepository.findByTenantIdAndMobileAndActiveTrue(tenant.getId(), request.mobile());
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with this mobile");
        }

        UserAccount user = users.get(0);
        return new AuthDtos.OtpVerifyResponse(user.getId(), user.getRole().name(), tenant.getId(), tenant.getSlug());
    }
}
