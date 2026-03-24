package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.auth.AuthResponse;
import com.hrm.project_spring.dto.auth.LoginRequest;
import com.hrm.project_spring.dto.user.UserRequest;
import com.hrm.project_spring.dto.user.UserResponse;
import com.hrm.project_spring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout() {
        return ResponseEntity.ok(authService.logout());
    }
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {
        return ResponseEntity.ok(authService.getProfile());
    }
}
