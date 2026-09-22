
package com.pisethjavaschool.propertyowner.service;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface OwnerDocumentUniquenessValidator {

    Mono<Void> validateForCreate(
            UUID ownerId,
            String documentTypeCode
    );

    Mono<Void> validateForUpdate(
            UUID ownerId,
            UUID documentId,
            String documentTypeCode
    );
}
