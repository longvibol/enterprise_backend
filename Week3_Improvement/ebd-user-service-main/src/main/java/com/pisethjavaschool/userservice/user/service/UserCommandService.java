package com.pisethjavaschool.userservice.user.service;

import java.util.UUID;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;
import com.pisethjavaschool.userservice.user.dto.ResetPasswordRequest;
import com.pisethjavaschool.userservice.user.dto.UpdateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface UserCommandService {
    Mono<UserResponse> create(CreateUserRequest request);
    Mono<UserResponse> update(UUID id, UpdateUserRequest request);
    Mono<Void> delete(UUID id);
    Mono<UserResponse> activate(UUID id);
    Mono<UserResponse> deactivate(UUID id);
    Mono<Void> resetPassword(UUID id, String newPassword);
}
