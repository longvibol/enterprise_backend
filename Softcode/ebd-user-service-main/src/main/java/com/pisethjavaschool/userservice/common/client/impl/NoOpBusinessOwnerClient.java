package com.pisethjavaschool.userservice.common.client.impl;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import com.pisethjavaschool.userservice.common.client.BusinessOwnerClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NoOpBusinessOwnerClient implements BusinessOwnerClient {
    @Override
    public Mono<UUID> createOwnerProfile(UUID userAccountId, BusinessOwnerRegistrationRequest request) {
        UUID organizationId = UUID.randomUUID();
        log.info("TODO call business-owner-service for Restaurant or KTV owner profile. userAccountId={}, generatedOwnerId={}", userAccountId, organizationId);
        return Mono.just(organizationId);
    }
}
