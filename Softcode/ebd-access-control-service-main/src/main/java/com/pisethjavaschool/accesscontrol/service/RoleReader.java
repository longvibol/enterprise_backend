package com.pisethjavaschool.accesscontrol.service;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.entity.AccessRole;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleReader {
    Mono<AccessRole> getById(UUID id);
    Flux<AccessRole> getAll();
}
