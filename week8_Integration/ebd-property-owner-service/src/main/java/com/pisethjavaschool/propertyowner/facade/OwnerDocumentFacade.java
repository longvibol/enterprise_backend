package com.pisethjavaschool.propertyowner.facade;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.CreateOwnerDocumentRequest;
import com.pisethjavaschool.propertyowner.dto.OwnerDocumentResponse;
import com.pisethjavaschool.propertyowner.dto.UpdateOwnerDocumentRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OwnerDocumentFacade {
	Mono<OwnerDocumentResponse> create(UUID ownerId, CreateOwnerDocumentRequest request);

	Mono<OwnerDocumentResponse> update(UUID ownerId, UUID documentId, UpdateOwnerDocumentRequest request);

	Flux<OwnerDocumentResponse> getByOwner(UUID ownerId);

	Mono<Void> delete(UUID ownerId, UUID documentId);
}
