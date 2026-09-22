package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.platform.common.pagination.PageUtils;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerSummaryResponse;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.facade.SearchPropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.repository.PropertyOwnerQueryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SearchPropertyOwnerFacadeImpl implements SearchPropertyOwnerFacade {
    private final PropertyOwnerQueryRepository queryRepository;
    private final PropertyOwnerMapper mapper;

    @Override
    public Mono<PageResponse<PropertyOwnerSummaryResponse>> search(UUID userId, OwnerType ownerType, OwnerVerificationStatus status, Boolean active, String keyword, int page, int size) {
        return queryRepository.search(userId, ownerType, status, active, keyword, page, size)
                .map(result -> PageUtils.toPageResponse(
                        result.items().stream().map(mapper::toSummary).toList(),
                        result.totalElements(), page, size));
    }
}
