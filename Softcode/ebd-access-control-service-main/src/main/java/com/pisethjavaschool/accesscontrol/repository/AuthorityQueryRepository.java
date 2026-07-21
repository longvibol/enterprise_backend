package com.pisethjavaschool.accesscontrol.repository;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.dto.PermissionCheckRequest;

import reactor.core.publisher.Mono;

public interface AuthorityQueryRepository {
    Mono<AuthorityResponse> getAuthorities(UUID userId);
    Mono<Boolean> hasPermission(PermissionCheckRequest request);
}
