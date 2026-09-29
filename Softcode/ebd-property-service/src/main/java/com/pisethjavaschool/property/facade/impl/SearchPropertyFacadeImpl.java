package com.pisethjavaschool.property.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.platform.common.pagination.PageUtils;
import com.pisethjavaschool.property.dto.PropertySummaryResponse;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.facade.SearchPropertyFacade;
import com.pisethjavaschool.property.mapper.PropertyMapper;
import com.pisethjavaschool.property.repository.PropertyQueryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SearchPropertyFacadeImpl
        implements SearchPropertyFacade {

    private final PropertyQueryRepository queryRepository;
    private final PropertyMapper mapper;

    @Override
    public Mono<PageResponse<PropertySummaryResponse>> search(
            UUID ownerId,
            PropertyStatus status,
            Boolean active,
            String keyword,
            int page,
            int size
    ) {
        return queryRepository
                .search(
                        ownerId,
                        status,
                        active,
                        keyword,
                        page,
                        size
                )
                .map(result ->
                        PageUtils.toPageResponse(
                                result.items().stream()
                                        .map(mapper::toSummary)
                                        .toList(),
                                result.totalElements(),
                                page,
                                size
                        )
                );
    }
}
