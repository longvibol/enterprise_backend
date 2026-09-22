package com.pisethjavaschool.accesscontrol.userrole.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;
import com.pisethjavaschool.accesscontrol.role.service.RoleReader;
import com.pisethjavaschool.accesscontrol.userrole.dto.AssignRoleByCodeRequest;
import com.pisethjavaschool.accesscontrol.userrole.dto.AssignRoleRequest;
import com.pisethjavaschool.accesscontrol.userrole.dto.UserRoleResponse;
import com.pisethjavaschool.accesscontrol.userrole.entity.UserRole;
import com.pisethjavaschool.accesscontrol.userrole.facade.UserRoleFacade;
import com.pisethjavaschool.accesscontrol.userrole.mapper.UserRoleMapper;
import com.pisethjavaschool.accesscontrol.userrole.repository.UserRoleRepository;
import com.pisethjavaschool.accesscontrol.userrole.service.ScopePolicy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleFacadeImpl implements UserRoleFacade {
    private final RoleReader roleReader;
    private final ScopePolicy scopePolicy;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper mapper;

    @Override
    @Transactional
    public Mono<UserRoleResponse> assignRole(UUID userId, AssignRoleRequest request) {
        return roleReader.getById(request.roleId())
                .then(scopePolicy.validateScope(request.scopeType(), request.scopeId()))
                .then(userRoleRepository.save(UserRole.builder()
                        .userId(userId)
                        .roleId(request.roleId())
                        .scopeType(request.scopeType())
                        .scopeId(request.scopeId())
                        .active(true)
                        .build()))
                .doOnNext(userRole -> log.info("Access role assigned: userId={}, roleId={}", userId, request.roleId()))
                .map(mapper::toResponse);
    }

    @Override
    public Flux<UserRoleResponse> getUserRoles(UUID userId) {
        return userRoleRepository.findByUserId(userId)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<Void> removeRole(UUID userId, UUID roleId) {
        return userRoleRepository.deleteByUserIdAndRoleId(userId, roleId)
                .doOnSuccess(ignored -> log.info("Access role removed: userId={}, roleId={}", userId, roleId));
    }
    
    @Override
    @Transactional
    public Mono<UserRoleResponse> assignRoleByCode(UUID userId, AssignRoleByCodeRequest request) {
        return roleReader.getByCode(request.roleCode())
                .flatMap(role -> scopePolicy.validateScope(role.getScopeType(), request.scopeId())
                        .then(findExistingAssignment(userId, role.getId(), role.getScopeType(), request.scopeId())
                                .flatMap(this::activateIfNecessary)
                                .switchIfEmpty(Mono.defer(() -> userRoleRepository.save(UserRole.builder()
                                        .userId(userId)
                                        .roleId(role.getId())
                                        .scopeType(role.getScopeType())
                                        .scopeId(request.scopeId())
                                        .active(true)
                                        .build())))))
                .doOnNext(userRole -> log.info("Access role assigned by code: userId={}, roleCode={}", userId, request.roleCode()))
                .map(mapper::toResponse);
    }

    private Mono<UserRole> findExistingAssignment(
            UUID userId,
            UUID roleId,
            ScopeType scopeType,
            UUID scopeId) {
        if (scopeId == null) {
            return userRoleRepository.findByUserIdAndRoleIdAndScopeTypeAndScopeIdIsNull(userId, roleId, scopeType);
        }
        return userRoleRepository.findByUserIdAndRoleIdAndScopeTypeAndScopeId(userId, roleId, scopeType, scopeId);
    }

    private Mono<UserRole> activateIfNecessary(UserRole userRole) {
        if (Boolean.TRUE.equals(userRole.getActive())) {
            return Mono.just(userRole);
        }
        userRole.setActive(true);
        return userRoleRepository.save(userRole);
    }
}
