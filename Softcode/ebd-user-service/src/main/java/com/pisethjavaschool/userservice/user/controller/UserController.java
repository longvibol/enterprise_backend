package com.pisethjavaschool.userservice.user.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;
import com.pisethjavaschool.userservice.user.dto.ResetPasswordRequest;
import com.pisethjavaschool.userservice.user.dto.UpdateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UserIdentityResponse;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.user.service.UserCommandService;
import com.pisethjavaschool.userservice.user.service.UserQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    @PostMapping
    public Mono<UserResponse> create(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return commandService.create(request);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findById(
            @PathVariable UUID id
    ) {
        return queryService.findById(id);
    }
    
    @GetMapping("/internal/by-keycloak-id/{keycloakUserId}")
    public Mono<UserIdentityResponse> findIdentityByKeycloakUserId(
            @PathVariable UUID keycloakUserId
    ) {
        return queryService.findIdentityByKeycloakUserId(keycloakUserId);
    }

    @GetMapping
    public Mono<PageResponse<UserResponse>> search(
            @RequestParam(required = false) UserType userType,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return queryService.search(userType, keyword, page, size);
    }

    @PutMapping("/{id}")
    public Mono<UserResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return commandService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(
            @PathVariable UUID id
    ) {
        return commandService.delete(id);
    }

    @PatchMapping("/{id}/activate")
    public Mono<UserResponse> activate(
            @PathVariable UUID id
    ) {
        return commandService.activate(id);
    }

    @PatchMapping("/{id}/deactivate")
    public Mono<UserResponse> deactivate(
            @PathVariable UUID id
    ) {
        return commandService.deactivate(id);
    }

    @PatchMapping("/{id}/reset-password")
    public Mono<Void> resetPassword(
            @PathVariable UUID id,
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        return commandService.resetPassword(id, request.newPassword());
    }
}
