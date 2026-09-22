package com.pisethjavaschool.accesscontrol.role.facade.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.accesscontrol.role.dto.CreateRoleRequest;
import com.pisethjavaschool.accesscontrol.role.dto.RoleResponse;
import com.pisethjavaschool.accesscontrol.role.dto.UpdateRoleRequest;
import com.pisethjavaschool.accesscontrol.common.dto.UpdateStatusRequest;
import com.pisethjavaschool.accesscontrol.role.facade.RoleManagementFacade;
import com.pisethjavaschool.accesscontrol.role.mapper.RoleMapper;
import com.pisethjavaschool.accesscontrol.common.service.AccessCodePolicy;
import com.pisethjavaschool.accesscontrol.role.service.RoleFactory;
import com.pisethjavaschool.accesscontrol.role.service.RoleReader;
import com.pisethjavaschool.accesscontrol.role.service.RoleUpdater;
import com.pisethjavaschool.accesscontrol.role.service.RoleWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleManagementFacadeImpl implements RoleManagementFacade {
    private final AccessCodePolicy accessCodePolicy;
    private final RoleFactory roleFactory;
    private final RoleReader reader;
    private final RoleUpdater updater;
    private final RoleWriter writer;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public Mono<RoleResponse> create(CreateRoleRequest request) {
        return accessCodePolicy.validateUniqueRoleCode(request.code())
                .thenReturn(roleFactory.create(request))
                .flatMap(writer::save)
                .doOnNext(role -> log.info("Access role created: roleId={}, code={}", role.getId(), role.getCode()))
                .map(mapper::toResponse);
    }

    @Override
    public Flux<RoleResponse> getAll() {
        return reader.getAll().map(mapper::toResponse);
    }

    @Override
    public Mono<RoleResponse> getById(UUID roleId) {
        return reader.getById(roleId).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<RoleResponse> update(UUID roleId, UpdateRoleRequest request) {
        return reader.getById(roleId)
                .flatMap(role -> accessCodePolicy.validateUniqueRoleCode(role.getId(), request.code())
                        .thenReturn(role))
                .doOnNext(role -> updater.update(role, request))
                .flatMap(writer::update)
                .doOnNext(role -> log.info("Access role updated: roleId={}, code={}", role.getId(), role.getCode()))
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<RoleResponse> updateStatus(UUID roleId, UpdateStatusRequest request) {
        return reader.getById(roleId)
                .flatMap(role -> writer.updateStatus(role, request.active()))
                .doOnNext(role -> log.info("Access role status updated: roleId={}, active={}", role.getId(), role.getActive()))
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public Mono<Void> delete(UUID roleId) {
        return reader.getById(roleId)
                .flatMap(writer::delete)
                .doOnSuccess(ignored -> log.info("Access role disabled: roleId={}", roleId));
    }
}
