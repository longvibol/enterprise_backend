package com.pisethjavaschool.property.repository;

import java.util.Set;
import java.util.UUID;

import com.pisethjavaschool.platform.common.pagination.PageResult;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;

import reactor.core.publisher.Mono;

public interface PropertyQueryRepository {

    Mono<PageResult<Property>> search(
            UUID ownerId,
            PropertyStatus status,
            Boolean active,
            String keyword,
            int page,
            int size
    );

    Mono<PageResult<Property>> searchDelegated(
            Set<UUID> propertyIds,
            PropertyStatus status,
            Boolean active,
            String keyword,
            int page,
            int size
    );
}
