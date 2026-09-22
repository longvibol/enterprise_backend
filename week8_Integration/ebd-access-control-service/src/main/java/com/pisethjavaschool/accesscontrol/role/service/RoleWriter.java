package com.pisethjavaschool.accesscontrol.role.service;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;

import reactor.core.publisher.Mono;

public interface RoleWriter {
    Mono<AccessRole> save(AccessRole role);
    Mono<AccessRole> update(AccessRole role);
    Mono<AccessRole> updateStatus(AccessRole role, Boolean active);
    Mono<Void> delete(AccessRole role);
}
