package com.pisethjavaschool.property.facade;

import java.util.UUID;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.property.dto.PropertySummaryResponse;
import com.pisethjavaschool.property.enums.PropertyStatus;

import reactor.core.publisher.Mono;

public interface SearchPropertyFacade {

    Mono<PageResponse<PropertySummaryResponse>> search(
            UUID ownerId,
            PropertyStatus status,
            Boolean active,
            String keyword,
            int page,
            int size);
}
