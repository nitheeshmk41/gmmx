package com.gmmx.mvp.service;

import com.gmmx.mvp.dto.AttendanceDtos;
import com.gmmx.mvp.model.Attendance;
import com.gmmx.mvp.model.DailyQr;
import com.gmmx.mvp.model.Tenant;
import com.gmmx.mvp.repository.AttendanceRepository;
import com.gmmx.mvp.repository.DailyQrRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AttendanceService {

    private final TenantResolver tenantResolver;
    private final DailyQrRepository dailyQrRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(TenantResolver tenantResolver, DailyQrRepository dailyQrRepository, AttendanceRepository attendanceRepository) {
        this.tenantResolver = tenantResolver;
        this.dailyQrRepository = dailyQrRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public AttendanceDtos.QrGenerateResponse generateDailyQr(AttendanceDtos.QrGenerateRequest request) {
        Tenant tenant = tenantResolver.bySlugOrThrow(request.tenantSlug());
        LocalDate today = LocalDate.now();

        DailyQr qr = dailyQrRepository.findByTenantIdAndQrDate(tenant.getId(), today)
                .orElseGet(() -> {
                    DailyQr dailyQr = new DailyQr();
                    dailyQr.setTenantId(tenant.getId());
                    dailyQr.setQrDate(today);
                    dailyQr.setQrToken(UUID.randomUUID().toString());
                    return dailyQr;
                });

        qr = dailyQrRepository.save(qr);
        return new AttendanceDtos.QrGenerateResponse(tenant.getSlug(), qr.getQrToken(), today);
    }

    @Transactional
    public AttendanceDtos.QrScanResponse scan(AttendanceDtos.QrScanRequest request) {
        if (request.memberId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "memberId is required");
        }

        Tenant tenant = tenantResolver.bySlugOrThrow(request.tenantSlug());
        LocalDate today = LocalDate.now();

        DailyQr qr = dailyQrRepository.findByTenantIdAndQrDate(tenant.getId(), today)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Daily QR not generated"));

        if (!qr.getQrToken().equals(request.qrToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid QR token");
        }

        boolean exists = attendanceRepository.existsByTenantIdAndMemberIdAndAttendanceDate(tenant.getId(), request.memberId(), today);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Attendance already marked for today");
        }

        Attendance attendance = new Attendance();
        attendance.setTenantId(tenant.getId());
        attendance.setMemberId(request.memberId());
        attendance.setAttendanceDate(today);
        attendance.setCheckedInAt(LocalDateTime.now());
        attendanceRepository.save(attendance);

        return new AttendanceDtos.QrScanResponse("Attendance marked", today);
    }
}
