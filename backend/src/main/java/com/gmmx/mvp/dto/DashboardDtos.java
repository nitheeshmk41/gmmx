package com.gmmx.mvp.dto;

public class DashboardDtos {
    public record DashboardSummaryResponse(
            String tenantSlug,
            long activeMembers,
            long activeTrainers,
            long todayAttendance,
            long trialDaysLeft,
            int memberLimit,
            int trainerLimit
    ) {}
}
