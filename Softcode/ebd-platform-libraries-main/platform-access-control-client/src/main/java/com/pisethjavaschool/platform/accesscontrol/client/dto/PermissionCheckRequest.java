package com.pisethjavaschool.platform.accesscontrol.client.dto;

import java.util.UUID;

import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessScopeType;

public record PermissionCheckRequest(
        UUID userId,
        String permissionCode,
        AccessScopeType scopeType,
        UUID scopeId) {
}