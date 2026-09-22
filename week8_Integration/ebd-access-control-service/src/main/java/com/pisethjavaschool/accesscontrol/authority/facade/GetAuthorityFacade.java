package com.pisethjavaschool.accesscontrol.authority.facade;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.authority.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckRequest;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckResponse;

import reactor.core.publisher.Mono;

public interface GetAuthorityFacade {
    Mono<AuthorityResponse> getByUserId(UUID userId);
    Mono<AuthorityResponse> getMe();
    Mono<PermissionCheckResponse> check(PermissionCheckRequest request);
}
