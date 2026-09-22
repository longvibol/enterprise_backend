package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.platform.security.PlatformAuditorContext;
import com.pisethjavaschool.propertyowner.dto.CreatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.facade.CreatePropertyOwnerFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerFactory;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerValidator;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePropertyOwnerFacadeImpl implements CreatePropertyOwnerFacade {
    /*
	private final CurrentUserReader currentUserReader;
    private final PropertyOwnerValidator validator;
    private final PropertyOwnerFactory factory;
    private final PropertyOwnerWriter writer;
    private final PropertyOwnerMapper mapper;

    @Override
    @Transactional
    public Mono<PropertyOwnerResponse> create(CreatePropertyOwnerRequest request) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> validator.validateUserHasNoOwner(userId).thenReturn(userId))
                .map(userId -> factory.createDraft(userId, request))
                .flatMap(writer::save)
                .doOnNext(owner -> log.info("Business owner created: ownerId={}, userId={}", owner.getId(), owner.getUserId()))
                .map(mapper::toResponse);
    }

	@Override
	public Mono<PropertyOwnerResponse> createForUser(UUID userId, CreatePropertyOwnerRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	private final CurrentUserReader currentUserReader;
    private final PropertyOwnerValidator validator;
    private final PropertyOwnerFactory factory;
    private final PropertyOwnerWriter writer;
    private final PropertyOwnerMapper mapper;

    @Override
    @Transactional
    public Mono<PropertyOwnerResponse> create(CreatePropertyOwnerRequest request) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> createForUser(userId, request));
    }

    @Override
    @Transactional
    public Mono<PropertyOwnerResponse> createForUser(UUID userId, CreatePropertyOwnerRequest request) {
        Mono<PropertyOwnerResponse> creation = validator.validateUserHasNoOwner(userId)
                .thenReturn(userId)
                .map(id -> factory.createDraft(id, request))
                .flatMap(writer::save)
                .doOnNext(owner -> log.info("Business owner created: ownerId={}, userId={}", owner.getId(), owner.getUserId()))
                .map(mapper::toResponse);

        return PlatformAuditorContext.withAuditorId(creation, userId);
    }
}
