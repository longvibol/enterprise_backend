package com.pisethjavaschool.accesscontrol.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.accesscontrol.dto.UpdatePermissionRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessPermission;
import com.pisethjavaschool.accesscontrol.service.PermissionUpdater;

@Service
public class PermissionUpdaterImpl implements PermissionUpdater {

    @Override
    public void update(AccessPermission permission, UpdatePermissionRequest request) {
        permission.setCode(request.code().trim().toUpperCase());
        permission.setName(request.name().trim());
        permission.setDescription(request.description());
        permission.setModule(request.module());
        permission.setActive(request.active() == null || request.active());
    }
}
