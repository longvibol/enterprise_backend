package com.pisethjavaschool.accesscontrol.userrole.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;
import com.pisethjavaschool.accesscontrol.common.exception.InvalidScopeException;
import com.pisethjavaschool.accesscontrol.userrole.service.ScopePolicy;

import reactor.core.publisher.Mono;

@Service
public class ScopePolicyImpl implements ScopePolicy {

    @Override
    public Mono<Void> validateScope(ScopeType scopeType, Object scopeId) {
        if (scopeType == ScopeType.GLOBAL && Objects.nonNull(scopeId)) {
            return Mono.error(new InvalidScopeException("GLOBAL scope must not have scopeId"));
        }

        if (scopeType != ScopeType.GLOBAL && Objects.isNull(scopeId)) {
            return Mono.error(new InvalidScopeException(scopeType + " scope requires scopeId"));
        }

        return Mono.empty();
    }
}
