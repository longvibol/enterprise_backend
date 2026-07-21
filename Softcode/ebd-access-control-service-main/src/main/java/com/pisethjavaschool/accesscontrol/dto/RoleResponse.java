package com.pisethjavaschool.accesscontrol.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.accesscontrol.enums.RoleType;
import com.pisethjavaschool.accesscontrol.enums.ScopeType;

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
