package com.pisethjavaschool.platform.common.audit;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface CurrentAuditorProvider {

    Mono<UUID> getCurrentAuditorId();

}