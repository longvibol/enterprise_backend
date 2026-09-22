package com.pisethjavaschool.accesscontrol.permission.service;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PermissionReader {
    Mono<AccessPermission> getById(UUID id);
    Flux<AccessPermission> getAll();
}
