package com.pisethjavaschool.accesscontrol.userrole.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

public record UserRoleResponse(
        UUID id,
        UUID userId,
        UUID roleId,
        ScopeType scopeType,
        UUID scopeId,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
