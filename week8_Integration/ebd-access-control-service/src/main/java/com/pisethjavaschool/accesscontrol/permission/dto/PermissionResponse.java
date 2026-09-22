package com.pisethjavaschool.accesscontrol.permission.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.accesscontrol.permission.enums.AccessModule;

public record PermissionResponse(
        UUID id,
        String code,
        String name,
        String description,
        AccessModule module,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {}
