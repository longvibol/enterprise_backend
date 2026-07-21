package com.pisethjavaschool.accesscontrol.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.accesscontrol.dto.AssignRoleRequest;
import com.pisethjavaschool.accesscontrol.dto.UserRoleResponse;
import com.pisethjavaschool.accesscontrol.entity.UserRole;
import com.pisethjavaschool.accesscontrol.facade.UserRoleFacade;
import com.pisethjavaschool.accesscontrol.mapper.AccessControlMapper;
import com.pisethjavaschool.accesscontrol.repository.UserRoleRepository;
import com.pisethjavaschool.accesscontrol.service.RoleReader;
import com.pisethjavaschool.accesscontrol.service.ScopePolicy;

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
    private final AccessControlMapper mapper;

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
                .map(mapper::toUserRoleResponse);
    }

    @Override
    public Flux<UserRoleResponse> getUserRoles(UUID userId) {
        return userRoleRepository.findByUserId(userId)
                .map(mapper::toUserRoleResponse);
    }

    @Override
    @Transactional
    public Mono<Void> removeRole(UUID userId, UUID roleId) {
        return userRoleRepository.deleteByUserIdAndRoleId(userId, roleId)
                .doOnSuccess(ignored -> log.info("Access role removed: userId={}, roleId={}", userId, roleId));
    }
}
