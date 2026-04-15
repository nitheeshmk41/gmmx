package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.AttendanceDtos;
import com.gmmx.mvp.dto.AuthDtos;
import com.gmmx.mvp.dto.DashboardDtos;
import com.gmmx.mvp.dto.TenantDtos;
import com.gmmx.mvp.dto.UserDtos;
import com.gmmx.mvp.service.AttendanceService;
import com.gmmx.mvp.service.AuthService;
import com.gmmx.mvp.service.DashboardService;
import com.gmmx.mvp.service.TenantService;
import com.gmmx.mvp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        AuthController.class,
        AttendanceController.class,
        DashboardController.class,
        TenantController.class,
        UserController.class
})
@Import(ApiExceptionHandler.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AttendanceService attendanceService;

    @MockBean
    private DashboardService dashboardService;

    @MockBean
    private TenantService tenantService;

    @MockBean
    private UserService userService;

    @Test
    void registerOwner_shouldReturnTenantAndOwnerDetails() throws Exception {
        UUID tenantId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID ownerId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(authService.registerOwner(any(AuthDtos.OwnerRegisterRequest.class)))
                .thenReturn(new AuthDtos.OwnerRegisterResponse(
                        tenantId,
                        "power-house-gym",
                        ownerId,
                        LocalDateTime.of(2026, 4, 14, 10, 15)
                ));

        mockMvc.perform(post("/api/auth/owner/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "ownerName": "John Doe",
                                  "mobile": "9876543210",
                                  "email": "john@example.com",
                                  "gymName": "Power House Gym",
                                  "location": "Bangalore",
                                  "slug": "power-house-gym",
                                  "password": "secret123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.slug").value("power-house-gym"))
                .andExpect(jsonPath("$.ownerId").value(ownerId.toString()))
                .andExpect(jsonPath("$.trialEndsAt").exists());
    }

    @Test
    void sendOtp_shouldReturnOtpPayload() throws Exception {
        when(authService.sendOtp(any(AuthDtos.OtpRequest.class)))
                .thenReturn(new AuthDtos.OtpResponse("OTP sent", 300, "123456"));

        mockMvc.perform(post("/api/auth/mobile/otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym",
                                  "mobile": "9876543210"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP sent"))
                .andExpect(jsonPath("$.expiresInSeconds").value(300))
                .andExpect(jsonPath("$.debugCode").value("123456"));
    }

    @Test
    void verifyOtp_shouldReturnUserSessionPayload() throws Exception {
        UUID userId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID tenantId = UUID.fromString("44444444-4444-4444-4444-444444444444");

        when(authService.verifyOtp(any(AuthDtos.OtpVerifyRequest.class)))
                .thenReturn(new AuthDtos.OtpVerifyResponse(userId, "MEMBER", tenantId, "power-house-gym"));

        mockMvc.perform(post("/api/auth/mobile/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym",
                                  "mobile": "9876543210",
                                  "code": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.role").value("MEMBER"))
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.tenantSlug").value("power-house-gym"));
    }

    @Test
    void generateQr_shouldReturnQrDetails() throws Exception {
        when(attendanceService.generateDailyQr(any(AttendanceDtos.QrGenerateRequest.class)))
                .thenReturn(new AttendanceDtos.QrGenerateResponse(
                        "power-house-gym",
                        "qr-token-value",
                        LocalDate.of(2026, 4, 14)
                ));

        mockMvc.perform(post("/api/attendance/qr/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenantSlug").value("power-house-gym"))
                .andExpect(jsonPath("$.qrToken").value("qr-token-value"))
                .andExpect(jsonPath("$.qrDate").exists());
    }

    @Test
    void scan_shouldReturnAttendanceMessage() throws Exception {
        when(attendanceService.scan(any(AttendanceDtos.QrScanRequest.class)))
                .thenReturn(new AttendanceDtos.QrScanResponse("Attendance marked", LocalDate.of(2026, 4, 14)));

        mockMvc.perform(post("/api/attendance/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym",
                                  "qrToken": "qr-token-value",
                                  "memberId": "55555555-5555-5555-5555-555555555555"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Attendance marked"))
                .andExpect(jsonPath("$.attendanceDate").exists());
    }

    @Test
    void dashboardSummary_shouldReturnAggregatedCounts() throws Exception {
        when(dashboardService.getSummary(eq("power-house-gym")))
                .thenReturn(new DashboardDtos.DashboardSummaryResponse(
                        "power-house-gym",
                        120,
                        8,
                        42,
                        12,
                        200,
                        20
                ));

        mockMvc.perform(get("/api/dashboard/summary").param("tenantSlug", "power-house-gym"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenantSlug").value("power-house-gym"))
                .andExpect(jsonPath("$.activeMembers").value(120))
                .andExpect(jsonPath("$.activeTrainers").value(8))
                .andExpect(jsonPath("$.todayAttendance").value(42))
                .andExpect(jsonPath("$.trialDaysLeft").value(12))
                .andExpect(jsonPath("$.memberLimit").value(200))
                .andExpect(jsonPath("$.trainerLimit").value(20));
    }

    @Test
    void createTenant_shouldReturnTenantInfo() throws Exception {
        UUID tenantId = UUID.fromString("66666666-6666-6666-6666-666666666666");

        when(tenantService.create(any(TenantDtos.TenantCreateRequest.class)))
                .thenReturn(new TenantDtos.TenantCreateResponse(tenantId, "power-house-gym"));

        mockMvc.perform(post("/api/tenant/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "gymName": "Power House Gym",
                                  "location": "Bangalore",
                                  "slug": "power-house-gym"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.slug").value("power-house-gym"));
    }

    @Test
    void getPublicGym_shouldReturnGymProfile() throws Exception {
        when(tenantService.getPublicBySlug(eq("power-house-gym")))
                .thenReturn(new TenantDtos.PublicGymResponse(
                        "power-house-gym",
                        "Power House Gym",
                        "Bangalore",
                        "24x7 fitness center",
                        "9876543210",
                        "#ff5500"
                ));

        mockMvc.perform(get("/api/public/power-house-gym"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("power-house-gym"))
                .andExpect(jsonPath("$.gymName").value("Power House Gym"))
                .andExpect(jsonPath("$.location").value("Bangalore"))
                .andExpect(jsonPath("$.about").value("24x7 fitness center"))
                .andExpect(jsonPath("$.contactPhone").value("9876543210"))
                .andExpect(jsonPath("$.themePrimary").value("#ff5500"));
    }

    @Test
    void addMember_shouldReturnCreatedUser() throws Exception {
        UUID userId = UUID.fromString("77777777-7777-7777-7777-777777777777");

        when(userService.addMember(any(UserDtos.CreateUserRequest.class)))
                .thenReturn(new UserDtos.UserResponse(userId, "Jane Member", "9876543210", "jane@example.com", "MEMBER"));

        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym",
                                  "fullName": "Jane Member",
                                  "mobile": "9876543210",
                                  "email": "jane@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.fullName").value("Jane Member"))
                .andExpect(jsonPath("$.mobile").value("9876543210"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.role").value("MEMBER"));
    }

    @Test
    void addTrainer_shouldReturnCreatedTrainer() throws Exception {
        UUID userId = UUID.fromString("88888888-8888-8888-8888-888888888888");

        when(userService.addTrainer(any(UserDtos.CreateUserRequest.class)))
                .thenReturn(new UserDtos.UserResponse(userId, "Mark Trainer", "9876543211", "mark@example.com", "TRAINER"));

        mockMvc.perform(post("/api/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "tenantSlug": "power-house-gym",
                                  "fullName": "Mark Trainer",
                                  "mobile": "9876543211",
                                  "email": "mark@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.fullName").value("Mark Trainer"))
                .andExpect(jsonPath("$.mobile").value("9876543211"))
                .andExpect(jsonPath("$.email").value("mark@example.com"))
                .andExpect(jsonPath("$.role").value("TRAINER"));
    }

    @Test
    void invalidOwnerRegister_shouldReturnValidationError() throws Exception {
        mockMvc.perform(post("/api/auth/owner/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.details", hasSize(7)));
    }
}

