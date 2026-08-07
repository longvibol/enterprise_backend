package com.pisethjavaschool.userservice.common.client;

import java.util.UUID;
import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import reactor.core.publisher.Mono;

public interface BusinessOwnerClient {
    Mono<UUID> createOwnerProfile(UUID userAccountId, BusinessOwnerRegistrationRequest request);
}
