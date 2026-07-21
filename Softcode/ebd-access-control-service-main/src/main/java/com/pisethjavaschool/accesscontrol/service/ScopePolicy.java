package com.pisethjavaschool.accesscontrol.service;

import com.pisethjavaschool.accesscontrol.enums.ScopeType;

import reactor.core.publisher.Mono;

public interface ScopePolicy {
    Mono<Void> validateScope(ScopeType scopeType, Object scopeId);
}
