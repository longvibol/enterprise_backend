package com.pisethjavaschool.accesscontrol.userrole.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;
import com.pisethjavaschool.accesscontrol.userrole.entity.UserRole;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRoleRepository extends ReactiveCrudRepository<UserRole, UUID> {
	Flux<UserRole> findByUserId(UUID userId);

	Flux<UserRole> findByUserIdAndActiveTrue(UUID userId);

	Mono<Void> deleteByUserIdAndRoleId(UUID userId, UUID roleId);

	Mono<UserRole> findByUserIdAndRoleIdAndScopeId(UUID userId, UUID roleId, UUID scopeId);

	Mono<UserRole> findByUserIdAndRoleIdAndScopeTypeAndScopeId(UUID userId, UUID roleId, ScopeType scopeType,
			UUID scopeId);

	Mono<UserRole> findByUserIdAndRoleIdAndScopeTypeAndScopeIdIsNull(UUID userId, UUID roleId, ScopeType scopeType);

}
