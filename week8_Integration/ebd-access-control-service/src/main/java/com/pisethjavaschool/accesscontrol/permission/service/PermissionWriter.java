package com.pisethjavaschool.accesscontrol.permission.service;

import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

import reactor.core.publisher.Mono;

public interface PermissionWriter {
    Mono<AccessPermission> save(AccessPermission permission);
    Mono<AccessPermission> update(AccessPermission permission);
    Mono<AccessPermission> updateStatus(AccessPermission permission, Boolean active);
    Mono<Void> delete(AccessPermission permission);
}
