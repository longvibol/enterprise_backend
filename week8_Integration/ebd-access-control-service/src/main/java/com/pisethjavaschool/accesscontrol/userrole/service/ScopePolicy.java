package com.pisethjavaschool.accesscontrol.userrole.service;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

import reactor.core.publisher.Mono;

public interface ScopePolicy {
    Mono<Void> validateScope(ScopeType scopeType, Object scopeId);
}
