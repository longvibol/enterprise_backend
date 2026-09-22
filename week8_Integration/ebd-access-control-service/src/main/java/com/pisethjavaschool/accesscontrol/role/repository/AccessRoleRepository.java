package com.pisethjavaschool.accesscontrol.role.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.accesscontrol.role.entity.AccessRole;

import reactor.core.publisher.Mono;

public interface AccessRoleRepository extends ReactiveCrudRepository<AccessRole, UUID> {
    Mono<Boolean> existsByCode(String code);
    Mono<AccessRole> findByCode(String code);
}
