package com.pisethjavaschool.propertyowner.repository;

import java.util.UUID;

import com.pisethjavaschool.platform.common.pagination.PageResult;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

import reactor.core.publisher.Mono;

public interface PropertyOwnerQueryRepository {
    Mono<PageResult<PropertyOwner>> search(UUID userId, OwnerType ownerType, OwnerVerificationStatus status, Boolean active, String keyword, int page, int size);
}
