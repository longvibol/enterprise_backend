package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerClient;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;
import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.property.exception.PropertyAccessDeniedException;
import com.pisethjavaschool.property.service.CurrentPropertyOwnerResolver;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrentPropertyOwnerResolverImpl implements CurrentPropertyOwnerResolver {
    private static final String VERIFIED = "VERIFIED";

    private final CurrentUserReader currentUserReader;
    private final PropertyOwnerClient propertyOwnerClient;

    @Override
    public Mono<PropertyOwnerSummary> resolveVerifiedActiveOwner() {
        return currentUserReader.getCurrentUserId()
                .flatMap(propertyOwnerClient::getByUserId)
                .switchIfEmpty(Mono.error(new PropertyAccessDeniedException("Property owner profile not found")))
                .flatMap(this::validateOwner);
    }

    private Mono<PropertyOwnerSummary> validateOwner(PropertyOwnerSummary owner) {
        if (!Boolean.TRUE.equals(owner.active())) {
            return Mono.error(new PropertyAccessDeniedException("Property owner is inactive"));
        }
        if (!VERIFIED.equals(owner.verificationStatus())) {
            return Mono.error(new PropertyAccessDeniedException("Property owner must be VERIFIED before creating properties"));
        }
        return Mono.just(owner);
    }
}