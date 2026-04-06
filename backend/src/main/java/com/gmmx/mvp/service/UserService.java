package com.gmmx.mvp.service;

import com.gmmx.mvp.dto.UserDtos;
import com.gmmx.mvp.model.Tenant;
import com.gmmx.mvp.model.UserAccount;
import com.gmmx.mvp.model.UserRole;
import com.gmmx.mvp.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserAccountRepository userAccountRepository;
    private final TenantResolver tenantResolver;

    public UserService(UserAccountRepository userAccountRepository, TenantResolver tenantResolver) {
        this.userAccountRepository = userAccountRepository;
        this.tenantResolver = tenantResolver;
    }

    public UserDtos.UserResponse addMember(UserDtos.CreateUserRequest request) {
        return createWithRole(request, UserRole.MEMBER);
    }

    public UserDtos.UserResponse addTrainer(UserDtos.CreateUserRequest request) {
        return createWithRole(request, UserRole.TRAINER);
    }

    private UserDtos.UserResponse createWithRole(UserDtos.CreateUserRequest request, UserRole role) {
        Tenant tenant = tenantResolver.bySlugOrThrow(request.tenantSlug());

        long currentRoleCount = userAccountRepository.countByTenantIdAndRoleAndActiveTrue(tenant.getId(), role);
        if (role == UserRole.MEMBER && currentRoleCount >= tenant.getMemberLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Member limit reached for current plan");
        }
        if (role == UserRole.TRAINER && currentRoleCount >= tenant.getTrainerLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trainer limit reached for current plan");
        }

        UserAccount account = new UserAccount();
        account.setTenantId(tenant.getId());
        account.setRole(role);
        account.setFullName(request.fullName());
        account.setMobile(request.mobile());
        account.setEmail(request.email());
        account.setActive(true);
        userAccountRepository.save(account);

        return new UserDtos.UserResponse(account.getId(), account.getFullName(), account.getMobile(), account.getEmail(), account.getRole().name());
    }
}
