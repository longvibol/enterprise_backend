package com.pisethjavaschool.accesscontrol.authority.repository;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.authority.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckRequest;

import reactor.core.publisher.Mono;

public interface AuthorityQueryRepository {
    Mono<AuthorityResponse> getAuthorities(UUID userId);
    Mono<Boolean> hasPermission(PermissionCheckRequest request);
}
