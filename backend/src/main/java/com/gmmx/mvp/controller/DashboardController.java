package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.DashboardDtos;
import com.gmmx.mvp.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardDtos.DashboardSummaryResponse summary(@RequestParam String tenantSlug) {
        return dashboardService.getSummary(tenantSlug);
    }
}
