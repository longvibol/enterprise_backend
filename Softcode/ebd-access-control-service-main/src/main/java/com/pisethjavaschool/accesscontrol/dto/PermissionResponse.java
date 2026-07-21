package com.pisethjavaschool.accesscontrol.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.accesscontrol.enums.AccessModule;

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
