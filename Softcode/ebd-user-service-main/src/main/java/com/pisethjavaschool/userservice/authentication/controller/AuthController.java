package com.pisethjavaschool.userservice.authentication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.userservice.authentication.dto.LoginRequest;
import com.pisethjavaschool.userservice.authentication.dto.LoginResponse;
import com.pisethjavaschool.userservice.authentication.dto.RefreshTokenRequest;
import com.pisethjavaschool.userservice.authentication.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
    
    @PostMapping("/refresh-token")
    public Mono<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return authService.refreshToken(request);
    }
}
