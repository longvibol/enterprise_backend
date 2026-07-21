package com.pisethjavaschool.accesscontrol.service;

import com.pisethjavaschool.accesscontrol.dto.UpdatePermissionRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessPermission;

public interface PermissionUpdater {
    void update(AccessPermission permission, UpdatePermissionRequest request);
}
