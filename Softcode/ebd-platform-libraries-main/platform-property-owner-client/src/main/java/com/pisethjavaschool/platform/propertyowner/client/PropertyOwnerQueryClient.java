package com.pisethjavaschool.platform.propertyowner.client;

import java.util.UUID;
import com.pisethjavaschool.platform.propertyowner.client.dto.PropertyOwnerSummary;
import reactor.core.publisher.Mono;

public interface PropertyOwnerQueryClient {
    Mono<PropertyOwnerSummary> getByUserId(UUID userId);
}
