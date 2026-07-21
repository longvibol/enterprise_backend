package com.pisethjavaschool.accesscontrol.service;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.entity.AccessRole;

import reactor.core.publisher.Mono;

public interface RoleWriter {
    Mono<AccessRole> save(AccessRole role);
    Mono<AccessRole> update(AccessRole role);
    Mono<AccessRole> updateStatus(AccessRole role, Boolean active);
    Mono<Void> delete(AccessRole role);
}
