package com.pisethjavaschool.propertyowner.service;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface PropertyOwnerValidator {
	Mono<Void> validateUserHasNoOwner(UUID userId);

	Mono<Void> validateDocumentType(String documentTypeCode);
}
