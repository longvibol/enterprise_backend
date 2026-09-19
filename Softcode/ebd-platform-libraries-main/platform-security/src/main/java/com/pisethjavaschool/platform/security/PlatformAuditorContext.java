package com.pisethjavaschool.platform.security;

import java.util.UUID;

import reactor.core.publisher.Mono;
public final class PlatformAuditorContext {
    public static final String AUDITOR_ID_CONTEXT_KEY = PlatformAuditorContext.class.getName() + ".auditorId";

    private PlatformAuditorContext() {
    }

    public static <T> Mono<T> withAuditorId(Mono<T> publisher, UUID auditorId) {
        return publisher.contextWrite(context -> context.put(AUDITOR_ID_CONTEXT_KEY, auditorId));
    }
}