package com.pisethjavaschool.userservice.common.client;

import java.util.UUID;
import com.pisethjavaschool.userservice.user.enums.UserType;
import reactor.core.publisher.Mono;

public interface AccessControlClient {
    Mono<Void> assignDefaultRole(UUID userId, UserType userType, UUID scopeId);
}
