package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.AttendanceDtos;
import com.gmmx.mvp.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/qr/generate")
    public AttendanceDtos.QrGenerateResponse generate(@Valid @RequestBody AttendanceDtos.QrGenerateRequest request) {
        return attendanceService.generateDailyQr(request);
    }

    @PostMapping("/scan")
    public AttendanceDtos.QrScanResponse scan(@Valid @RequestBody AttendanceDtos.QrScanRequest request) {
        return attendanceService.scan(request);
    }
}
