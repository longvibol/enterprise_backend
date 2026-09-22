package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.dto.UpdatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.facade.UpdatePropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerReader;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerUpdater;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerWriter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdatePropertyOwnerFacadeImpl implements UpdatePropertyOwnerFacade {
    private final CurrentUserReader currentUserReader;
    private final PropertyOwnerReader reader;
    private final PropertyOwnerUpdater updater;
    private final PropertyOwnerWriter writer;
    private final PropertyOwnerMapper mapper;

    @Override
    @Transactional
    public Mono<PropertyOwnerResponse> update(UUID id, UpdatePropertyOwnerRequest request) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> reader.getOwnedOwner(id, userId))
                .map(owner -> updater.update(owner, request))
                .flatMap(writer::save)
                .map(mapper::toResponse);
    }
}
