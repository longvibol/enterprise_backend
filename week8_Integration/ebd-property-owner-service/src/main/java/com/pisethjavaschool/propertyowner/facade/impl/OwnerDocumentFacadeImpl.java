package com.pisethjavaschool.propertyowner.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.propertyowner.dto.CreateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.dto.OwnerDocumentResponse;
import com.pisethjavaschool.propertyowner.dto.UpdateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.exception.OwnerDocumentNotFoundException;
import com.pisethjavaschool.propertyowner.facade.OwnerDocumentFacade;
import com.pisethjavaschool.propertyowner.mapper.PropertyOwnerMapper;
import com.pisethjavaschool.propertyowner.repository.OwnerDocumentRepository;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentFactory;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentUpdater;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentUniquenessValidator;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerReader;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OwnerDocumentFacadeImpl implements OwnerDocumentFacade {
    private final CurrentUserReader currentUserReader;
    private final PropertyOwnerReader ownerReader;
    private final PropertyOwnerValidator validator;
    private final OwnerDocumentFactory factory;
    private final OwnerDocumentUpdater updater;
    private final OwnerDocumentUniquenessValidator uniquenessValidator;
    private final OwnerDocumentRepository repository;
    private final PropertyOwnerMapper mapper;

    @Override
    @Transactional
    public Mono<OwnerDocumentResponse> create(UUID ownerId, CreateOwnerDocumentRequest request) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> ownerReader.getOwnedOwner(ownerId, userId))
                .then(validator.validateDocumentType(request.documentTypeCode()))
                .then(uniquenessValidator.validateForCreate(
                        ownerId,
                        request.documentTypeCode()
                ))
                .thenReturn(factory.create(ownerId, request))
                .flatMap(repository::save)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<OwnerDocumentResponse> update(UUID ownerId, UUID documentId, UpdateOwnerDocumentRequest request) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> ownerReader.getOwnedOwner(ownerId, userId))
                .then(validator.validateDocumentType(request.documentTypeCode()))
                .then(uniquenessValidator.validateForUpdate(
                        ownerId,
                        documentId,
                        request.documentTypeCode()
                ))
                .then(repository.findByIdAndOwnerId(documentId, ownerId).switchIfEmpty(Mono.error(new OwnerDocumentNotFoundException())))
                .map(document -> updater.update(document, request))
                .flatMap(repository::save)
                .map(mapper::toResponse);
    }

    @Override
    public Flux<OwnerDocumentResponse> getByOwner(UUID ownerId) {
        return repository.findByOwnerIdOrderByCreatedAtDesc(ownerId).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<Void> delete(UUID ownerId, UUID documentId) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> ownerReader.getOwnedOwner(ownerId, userId))
                .then(repository.findByIdAndOwnerId(documentId, ownerId).switchIfEmpty(Mono.error(new OwnerDocumentNotFoundException())))
                .flatMap(document -> {
                    document.setActive(false);
                    return repository.save(document).then();
                });
    }
}
