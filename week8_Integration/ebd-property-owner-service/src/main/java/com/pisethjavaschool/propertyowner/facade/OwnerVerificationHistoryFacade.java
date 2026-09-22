package com.pisethjavaschool.propertyowner.facade;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.OwnerVerificationHistoryResponse;

import reactor.core.publisher.Flux;

public interface OwnerVerificationHistoryFacade {
	Flux<OwnerVerificationHistoryResponse> getByOwner(UUID ownerId);
}
