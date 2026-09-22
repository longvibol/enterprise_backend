package com.pisethjavaschool.accesscontrol.permission.service;

import com.pisethjavaschool.accesscontrol.permission.dto.CreatePermissionRequest;
import com.pisethjavaschool.accesscontrol.permission.entity.AccessPermission;

public interface PermissionFactory {
    AccessPermission create(CreatePermissionRequest request);
}
