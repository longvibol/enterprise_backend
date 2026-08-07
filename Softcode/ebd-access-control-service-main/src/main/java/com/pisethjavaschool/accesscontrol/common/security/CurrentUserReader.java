package com.pisethjavaschool.accesscontrol.common.security;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.platform.common.audit.CurrentAuditorProvider;
import com.pisethjavaschool.platform.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CurrentUserReader {

    private final CurrentAuditorProvider currentAuditorProvider;

    public Mono<UUID> getCurrentUserId() {
        return currentAuditorProvider.getCurrentAuditorId()
                .switchIfEmpty(Mono.error(new UnauthorizedException(
                        "CURRENT_USER_NOT_FOUND",
                        "Current authenticated user not found"
                )));
    }
}
