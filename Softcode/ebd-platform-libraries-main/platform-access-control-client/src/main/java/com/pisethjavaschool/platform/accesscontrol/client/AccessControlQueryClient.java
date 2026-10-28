package com.pisethjavaschool.platform.accesscontrol.client;

import java.util.UUID;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;
import reactor.core.publisher.Mono;

public interface AccessControlQueryClient {
    Mono<Boolean> hasPermission(UUID userId, String permissionCode, AccessScopeType scopeType, UUID scopeId);
}
