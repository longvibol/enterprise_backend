package com.pisethjavaschool.accesscontrol.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.enums.ScopeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionCheckRequest(
        @NotNull UUID userId,
        @NotBlank String permissionCode,
        ScopeType scopeType,
        UUID scopeId
) {}
