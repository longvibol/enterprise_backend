package com.pisethjavaschool.accesscontrol.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.dto.PermissionCheckRequest;
import com.pisethjavaschool.accesscontrol.dto.PermissionCheckResponse;

import reactor.core.publisher.Mono;

public interface GetAuthorityFacade {
    Mono<AuthorityResponse> getByUserId(UUID userId);
    Mono<AuthorityResponse> getMe();
    Mono<PermissionCheckResponse> check(PermissionCheckRequest request);
}
