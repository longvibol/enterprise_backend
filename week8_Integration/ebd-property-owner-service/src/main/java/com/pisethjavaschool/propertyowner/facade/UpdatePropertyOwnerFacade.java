package com.pisethjavaschool.propertyowner.facade;

import java.util.UUID;

import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.dto.UpdatePropertyOwnerRequest;

import reactor.core.publisher.Mono;

public interface UpdatePropertyOwnerFacade {
	Mono<PropertyOwnerResponse> update(UUID id, UpdatePropertyOwnerRequest request);
}
