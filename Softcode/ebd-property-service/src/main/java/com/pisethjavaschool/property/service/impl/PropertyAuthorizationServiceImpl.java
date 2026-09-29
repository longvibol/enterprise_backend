package com.pisethjavaschool.property.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.exception.PropertyAccessDeniedException;
import com.pisethjavaschool.property.service.PropertyAuthorizationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyAuthorizationServiceImpl implements PropertyAuthorizationService {
    private final AccessControlClient accessControlClient;

    @Override
    public Mono<Void> validateCreate(UUID userId, UUID organizationId, BusinessType businessType) {
        String permission = switch (businessType) {
            case RESTAURANT -> "RESTAURANT_CREATE";
            case KTV -> "KTV_CREATE";
        };
        return accessControlClient
                .hasPermission(userId, permission, AccessScopeType.ORGANIZATION, organizationId)
                .flatMap(allowed -> allowed
                        ? Mono.empty()
                        : Mono.error(new PropertyAccessDeniedException(
                                "Missing permission " + permission + " for organization " + organizationId)));
    }
}