package com.pisethjavaschool.accesscontrol.permission.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;
import com.pisethjavaschool.accesscontrol.common.exception.AccessControlNotFoundException;
import com.pisethjavaschool.accesscontrol.permission.repository.AccessPermissionRepository;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionReader;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PermissionReaderImpl implements PermissionReader {
    private final AccessPermissionRepository repository;

    @Override
    public Mono<AccessPermission> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccessControlNotFoundException("Permission not found: " + id)));
    }

    @Override
    public Flux<AccessPermission> getAll() {
        return repository.findAll();
    }
}
