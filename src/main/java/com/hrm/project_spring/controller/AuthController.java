package com.hrm.project_spring.controller;
import com.hrm.project_spring.dto.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .status(200)
                .message("Đăng ký thành công")
                .data(authService.register(request))
                .build());
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .status(200)
                .message("Đăng nhập thành công")
                .data(authService.login(request))
                .build());
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<AuthResponse>> logout() {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .status(200)
                .message("Đăng xuất thành công")
                .data(authService.logout())
                .build());
    }
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile() {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .status(200)
                .message("Lấy thông tin thành công")
                .data(authService.getProfile())
                .build());
    }
}
