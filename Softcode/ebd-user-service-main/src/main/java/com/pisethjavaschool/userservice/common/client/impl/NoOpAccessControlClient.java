package com.pisethjavaschool.userservice.common.client.impl;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.common.client.AccessControlClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NoOpAccessControlClient implements AccessControlClient {
    @Override
    public Mono<Void> assignDefaultRole(UUID userId, UserType userType, UUID scopeId) {
        log.info("TODO call access-control-service to assign role. userId={}, userType={}, scopeId={}", userId, userType, scopeId);
        return Mono.empty();
    }
}
