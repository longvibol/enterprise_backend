package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.facade.GetPropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerReader;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetPropertyOwnerFacadeImpl implements GetPropertyOwnerFacade {
    private final CurrentUserReader currentUserReader;
    private final PropertyOwnerReader reader;
    private final PropertyOwnerMapper mapper;

    @Override
    public Mono<PropertyOwnerResponse> getById(UUID id) {
        return reader.getById(id).map(mapper::toResponse);
    }

    @Override
    public Mono<PropertyOwnerResponse> getByUserId(UUID userId) {
        return reader.getByUserId(userId)
                .map(mapper::toResponse);
    }

    @Override
    public Flux<PropertyOwnerResponse> getByUserIds(
            Collection<UUID> userIds
    ) {
        return reader.getByUserIds(userIds)
                .map(mapper::toResponse);
    }

    @Override
    public Mono<PropertyOwnerResponse> getMe() {
        return currentUserReader.getCurrentUserId()
                .flatMap(reader::getByUserId)
                .map(mapper::toResponse);
    }
}
