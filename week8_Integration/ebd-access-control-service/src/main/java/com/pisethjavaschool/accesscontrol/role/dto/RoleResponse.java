package com.pisethjavaschool.accesscontrol.role.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.accesscontrol.role.enums.RoleType;
import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

public record RoleResponse(
        UUID id,
        String code,
        String name,
        String description,
        RoleType roleType,
        ScopeType scopeType,
        Boolean systemRole,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
