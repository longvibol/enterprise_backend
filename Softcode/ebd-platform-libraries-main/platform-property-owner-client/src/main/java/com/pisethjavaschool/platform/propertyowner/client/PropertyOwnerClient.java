package com.pisethjavaschool.platform.propertyowner.client;

import java.util.UUID;

import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;

import reactor.core.publisher.Mono;
public interface PropertyOwnerClient {
    Mono<UUID> createOwner(CreatePropertyOwnerCommand command);
    
    Mono<PropertyOwnerSummary> getByUserId(UUID userId);
}
