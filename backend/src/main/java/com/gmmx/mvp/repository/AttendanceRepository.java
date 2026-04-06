package com.gmmx.mvp.repository;

import com.gmmx.mvp.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    boolean existsByTenantIdAndMemberIdAndAttendanceDate(UUID tenantId, UUID memberId, LocalDate attendanceDate);

    long countByTenantIdAndAttendanceDate(UUID tenantId, LocalDate attendanceDate);
}
