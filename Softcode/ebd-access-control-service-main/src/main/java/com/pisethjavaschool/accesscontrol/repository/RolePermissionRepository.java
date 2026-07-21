package com.pisethjavaschool.accesscontrol.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.accesscontrol.entity.RolePermission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolePermissionRepository extends ReactiveCrudRepository<RolePermission, UUID> {
    Flux<RolePermission> findByRoleId(UUID roleId);
    Mono<Void> deleteByRoleId(UUID roleId);
    Mono<Void> deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
    Mono<Boolean> existsByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}
