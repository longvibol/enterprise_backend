package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.entity.Property;
import reactor.core.publisher.Mono;

public interface PropertyOwnershipPolicy {
    Mono<Void> verifyOwner(Property property);
}
