package com.gmmx.mvp.service;

import com.gmmx.mvp.dto.DashboardDtos;
import com.gmmx.mvp.model.Tenant;
import com.gmmx.mvp.model.UserRole;
import com.gmmx.mvp.repository.AttendanceRepository;
import com.gmmx.mvp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class DashboardService {

    private final TenantResolver tenantResolver;
    private final UserAccountRepository userAccountRepository;
    private final AttendanceRepository attendanceRepository;

    public DashboardService(TenantResolver tenantResolver, UserAccountRepository userAccountRepository, AttendanceRepository attendanceRepository) {
        this.tenantResolver = tenantResolver;
        this.userAccountRepository = userAccountRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public DashboardDtos.DashboardSummaryResponse getSummary(String tenantSlug) {
        Tenant tenant = tenantResolver.bySlugOrThrow(tenantSlug);
        long members = userAccountRepository.countByTenantIdAndRoleAndActiveTrue(tenant.getId(), UserRole.MEMBER);
        long trainers = userAccountRepository.countByTenantIdAndRoleAndActiveTrue(tenant.getId(), UserRole.TRAINER);
        long attendance = attendanceRepository.countByTenantIdAndAttendanceDate(tenant.getId(), LocalDate.now());
        long daysLeft = Math.max(0, ChronoUnit.DAYS.between(LocalDate.now(), tenant.getTrialEndAt().toLocalDate()));

        return new DashboardDtos.DashboardSummaryResponse(
                tenant.getSlug(),
                members,
                trainers,
                attendance,
                daysLeft,
                tenant.getMemberLimit(),
                tenant.getTrainerLimit()
        );
    }
}
