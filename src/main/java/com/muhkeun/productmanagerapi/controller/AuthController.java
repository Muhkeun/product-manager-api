package com.muhkeun.productmanagerapi.controller;

import com.muhkeun.productmanagerapi.model.dto.LoginRequest;
import com.muhkeun.productmanagerapi.model.dto.LoginResponse;
import com.muhkeun.productmanagerapi.model.dto.SignupRequest;
import com.muhkeun.productmanagerapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자를 인증하고 액세스 토큰을 발급합니다.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String accessToken = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(accessToken));
    }
}
