package com.pisethjavaschool.accesscontrol.permission.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.permission.dto.CreatePermissionRequest;
import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;
import com.pisethjavaschool.accesscontrol.permission.service.PermissionFactory;

@Service
public class PermissionFactoryImpl implements PermissionFactory {

    @Override
    public AccessPermission create(CreatePermissionRequest request) {
        return AccessPermission.builder()
                .code(request.code().trim().toUpperCase())
                .name(request.name().trim())
                .description(request.description())
                .module(request.module())
                .active(request.active() == null || request.active())
                .build();
    }
}
