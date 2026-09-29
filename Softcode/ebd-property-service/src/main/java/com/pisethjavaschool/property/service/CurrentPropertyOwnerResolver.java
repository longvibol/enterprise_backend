package com.pisethjavaschool.property.service;

import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;

import reactor.core.publisher.Mono;

public interface CurrentPropertyOwnerResolver {
    Mono<PropertyOwnerSummary> resolveVerifiedActiveOwner();
}