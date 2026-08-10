package com.pisethjavaschool.userservice.authentication.service;

import com.pisethjavaschool.userservice.authentication.dto.LoginRequest;
import com.pisethjavaschool.userservice.authentication.dto.LoginResponse;
import com.pisethjavaschool.userservice.authentication.dto.RefreshTokenRequest;

import reactor.core.publisher.Mono;

public interface AuthService {
	Mono<LoginResponse> login(LoginRequest request);

	Mono<LoginResponse> refreshToken(RefreshTokenRequest request);
}
