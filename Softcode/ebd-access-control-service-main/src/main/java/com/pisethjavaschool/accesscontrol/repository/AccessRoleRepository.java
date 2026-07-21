package com.pisethjavaschool.accesscontrol.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.accesscontrol.entity.AccessRole;

import reactor.core.publisher.Mono;

public interface AccessRoleRepository extends ReactiveCrudRepository<AccessRole, UUID> {
    Mono<Boolean> existsByCode(String code);
    Mono<AccessRole> findByCode(String code);
}
