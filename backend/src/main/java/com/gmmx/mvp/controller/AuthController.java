package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.AuthDtos;
import com.gmmx.mvp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/owner/register")
    public AuthDtos.OwnerRegisterResponse registerOwner(@Valid @RequestBody AuthDtos.OwnerRegisterRequest request) {
        return authService.registerOwner(request);
    }

    @PostMapping("/mobile/otp")
    public AuthDtos.OtpResponse sendOtp(@Valid @RequestBody AuthDtos.OtpRequest request) {
        return authService.sendOtp(request);
    }

    @PostMapping("/mobile/verify")
    public AuthDtos.OtpVerifyResponse verifyOtp(@Valid @RequestBody AuthDtos.OtpVerifyRequest request) {
        return authService.verifyOtp(request);
    }
}
