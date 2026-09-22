
package com.pisethjavaschool.propertyowner.service;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface OwnerDocumentReviewService {

    Mono<Void> verifyPendingDocuments(UUID ownerId);
}
