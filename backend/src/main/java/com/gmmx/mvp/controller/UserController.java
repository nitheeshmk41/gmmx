package com.gmmx.mvp.controller;

import com.gmmx.mvp.dto.UserDtos;
import com.gmmx.mvp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/member")
    public UserDtos.UserResponse addMember(@Valid @RequestBody UserDtos.CreateUserRequest request) {
        return userService.addMember(request);
    }

    @PostMapping("/trainer")
    public UserDtos.UserResponse addTrainer(@Valid @RequestBody UserDtos.CreateUserRequest request) {
        return userService.addTrainer(request);
    }
}
