package com.pisethjavaschool.accesscontrol.permission.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.accesscontrol.permission.dto.CreatePermissionRequest;
import com.pisethjavaschool.accesscontrol.permission.dto.PermissionResponse;
import com.pisethjavaschool.accesscontrol.permission.dto.UpdatePermissionRequest;
import com.pisethjavaschool.accesscontrol.common.dto.UpdateStatusRequest;
import com.pisethjavaschool.accesscontrol.permission.facade.PermissionManagementFacade;
import com.pisethjavaschool.accesscontrol.permission.mapper.PermissionMapper;
import com.pisethjavaschool.accesscontrol.common.service.AccessCodePolicy;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionFactory;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionReader;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionUpdater;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionManagementFacadeImpl implements PermissionManagementFacade {
    private final AccessCodePolicy accessCodePolicy;
    private final PermissionFactory permissionFactory;
    private final PermissionReader reader;
    private final PermissionUpdater updater;
    private final PermissionWriter writer;
    private final PermissionMapper mapper;

    @Override
    @Transactional
    public Mono<PermissionResponse> create(CreatePermissionRequest request) {
        return accessCodePolicy.validateUniquePermissionCode(request.code())
                .thenReturn(permissionFactory.create(request))
                .flatMap(writer::save)
                .doOnNext(permission -> log.info("Access permission created: permissionId={}, code={}", permission.getId(), permission.getCode()))
                .map(mapper::toResponse);
    }

    @Override
    public Flux<PermissionResponse> getAll() {
        return reader.getAll().map(mapper::toResponse);
    }

    @Override
    public Mono<PermissionResponse> getById(UUID permissionId) {
        return reader.getById(permissionId).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<PermissionResponse> update(UUID permissionId, UpdatePermissionRequest request) {
        return reader.getById(permissionId)
                .flatMap(permission -> accessCodePolicy.validateUniquePermissionCode(permission.getId(), request.code())
                        .thenReturn(permission))
                .doOnNext(permission -> updater.update(permission, request))
                .flatMap(writer::update)
                .doOnNext(permission -> log.info("Access permission updated: permissionId={}, code={}", permission.getId(), permission.getCode()))
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<PermissionResponse> updateStatus(UUID permissionId, UpdateStatusRequest request) {
        return reader.getById(permissionId)
                .flatMap(permission -> writer.updateStatus(permission, request.active()))
                .doOnNext(permission -> log.info("Access permission status updated: permissionId={}, active={}", permission.getId(), permission.getActive()))
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<Void> delete(UUID permissionId) {
        return reader.getById(permissionId)
                .flatMap(writer::delete)
                .doOnSuccess(ignored -> log.info("Access permission disabled: permissionId={}", permissionId));
    }
}
