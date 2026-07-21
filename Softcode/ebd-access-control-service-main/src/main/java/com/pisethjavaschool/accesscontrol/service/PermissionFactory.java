package com.pisethjavaschool.accesscontrol.service;

import com.pisethjavaschool.accesscontrol.dto.CreatePermissionRequest;
import com.pisethjavaschool.accesscontrol.entity.AccessPermission;

public interface PermissionFactory {
    AccessPermission create(CreatePermissionRequest request);
}
