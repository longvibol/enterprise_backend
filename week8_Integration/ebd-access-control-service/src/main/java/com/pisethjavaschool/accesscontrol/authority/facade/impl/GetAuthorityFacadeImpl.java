package com.pisethjavaschool.accesscontrol.authority.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.accesscontrol.authority.dto.AuthorityResponse;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckRequest;
import com.pisethjavaschool.accesscontrol.authority.dto.PermissionCheckResponse;
import com.pisethjavaschool.accesscontrol.authority.facade.GetAuthorityFacade;
import com.pisethjavaschool.accesscontrol.authority.repository.AuthorityQueryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetAuthorityFacadeImpl implements GetAuthorityFacade {
    private final CurrentUserReader currentUserReader;
    private final AuthorityQueryRepository authorityQueryRepository;

    @Override
    public Mono<AuthorityResponse> getByUserId(UUID userId) {
        return authorityQueryRepository.getAuthorities(userId);
    }

    @Override
    public Mono<AuthorityResponse> getMe() {
        return currentUserReader.getCurrentUserId()
                .flatMap(authorityQueryRepository::getAuthorities);
    }

    @Override
    public Mono<PermissionCheckResponse> check(PermissionCheckRequest request) {
        return authorityQueryRepository.hasPermission(request)
                .map(PermissionCheckResponse::new);
    }
}
