package com.pisethjavaschool.accesscontrol.permission.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccessPermissionRepository extends ReactiveCrudRepository<AccessPermission, UUID> {
    Mono<Boolean> existsByCode(String code);
    Mono<AccessPermission> findByCode(String code);
    Flux<AccessPermission> findAllByIdIn(Collection<UUID> ids);
}
