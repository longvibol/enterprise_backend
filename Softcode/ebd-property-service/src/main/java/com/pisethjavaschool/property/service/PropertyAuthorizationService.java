package com.pisethjavaschool.property.service;

import java.util.UUID;

import com.pisethjavaschool.property.enums.BusinessType;

import reactor.core.publisher.Mono;

public interface PropertyAuthorizationService {
    Mono<Void> validateCreate(UUID userId, UUID organizationId, BusinessType businessType);
}