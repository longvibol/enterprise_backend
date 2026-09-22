package com.pisethjavaschool.accesscontrol.permission.service;

import com.pisethjavaschool.accesscontrol.permission.dto.UpdatePermissionRequest;
import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

public interface PermissionUpdater {
    void update(AccessPermission permission, UpdatePermissionRequest request);
}
