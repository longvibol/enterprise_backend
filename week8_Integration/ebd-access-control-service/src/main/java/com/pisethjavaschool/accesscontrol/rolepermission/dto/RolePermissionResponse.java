package com.pisethjavaschool.accesscontrol.rolepermission.dto;

import java.util.UUID;

public record RolePermissionResponse(
        UUID id,
        UUID roleId,
        UUID permissionId
) {}
