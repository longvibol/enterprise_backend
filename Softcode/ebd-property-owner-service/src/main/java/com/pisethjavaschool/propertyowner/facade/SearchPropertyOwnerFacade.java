package com.pisethjavaschool.propertyowner.facade;

import java.util.UUID;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerSummaryResponse;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

import reactor.core.publisher.Mono;

public interface SearchPropertyOwnerFacade {
	Mono<PageResponse<PropertyOwnerSummaryResponse>> search(UUID userId, OwnerType ownerType,
			OwnerVerificationStatus status, Boolean active, String keyword, int page, int size);
}
