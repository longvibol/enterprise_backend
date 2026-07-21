package com.pisethjavaschool.accesscontrol.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.accesscontrol.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.dto.ReplaceRolePermissionsRequest;
import com.pisethjavaschool.accesscontrol.dto.RolePermissionResponse;
import com.pisethjavaschool.accesscontrol.entity.RolePermission;
import com.pisethjavaschool.accesscontrol.facade.RolePermissionFacade;
import com.pisethjavaschool.accesscontrol.mapper.AccessControlMapper;
import com.pisethjavaschool.accesscontrol.repository.AccessPermissionRepository;
import com.pisethjavaschool.accesscontrol.repository.RolePermissionRepository;
import com.pisethjavaschool.accesscontrol.service.PermissionReader;
import com.pisethjavaschool.accesscontrol.service.RoleReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RolePermissionFacadeImpl implements RolePermissionFacade {
    private final RoleReader roleReader;
    private final PermissionReader permissionReader;
    private final AccessPermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final AccessControlMapper mapper;

    @Override
    @Transactional
    public Mono<Void> replaceRolePermissions(UUID roleId, ReplaceRolePermissionsRequest request) {
        return roleReader.getById(roleId)
                .thenMany(permissionRepository.findAllByIdIn(request.permissionIds()))
                .map(permission -> permission.getId())
                .collectList()
                .flatMapMany(permissionIds -> rolePermissionRepository.deleteByRoleId(roleId)
                        .thenMany(Flux.fromIterable(permissionIds)))
                .map(permissionId -> RolePermission.builder()
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .build())
                .flatMap(rolePermissionRepository::save)
                .then()
                .doOnSuccess(ignored -> log.info("Access role permissions replaced: roleId={}, permissionCount={}", roleId, request.permissionIds().size()));
    }

    @Override
    public Flux<PermissionResponse> getPermissions(UUID roleId) {
        return roleReader.getById(roleId)
                .thenMany(rolePermissionRepository.findByRoleId(roleId))
                .flatMap(rolePermission -> permissionReader.getById(rolePermission.getPermissionId()))
                .map(mapper::toPermissionResponse);
    }

    @Override
    @Transactional
    public Mono<RolePermissionResponse> addPermission(UUID roleId, UUID permissionId) {
        return roleReader.getById(roleId)
                .then(permissionReader.getById(permissionId))
                .then(rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId))
                .flatMap(exists -> exists
                        ? rolePermissionRepository.findByRoleId(roleId)
                            .filter(item -> item.getPermissionId().equals(permissionId))
                            .next()
                        : rolePermissionRepository.save(RolePermission.builder()
                            .roleId(roleId)
                            .permissionId(permissionId)
                            .build()))
                .doOnNext(rolePermission -> log.info("Access role permission added: roleId={}, permissionId={}", roleId, permissionId))
                .map(mapper::toRolePermissionResponse);
    }

    @Override
    @Transactional
    public Mono<Void> removePermission(UUID roleId, UUID permissionId) {
        return roleReader.getById(roleId)
                .then(permissionReader.getById(permissionId))
                .then(rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId))
                .doOnSuccess(ignored -> log.info("Access role permission removed: roleId={}, permissionId={}", roleId, permissionId));
    }
}
