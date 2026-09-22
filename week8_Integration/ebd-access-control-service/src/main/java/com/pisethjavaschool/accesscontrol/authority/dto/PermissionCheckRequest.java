package com.pisethjavaschool.accesscontrol.authority.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionCheckRequest(
        @NotNull UUID userId,
        @NotBlank String permissionCode,
        ScopeType scopeType,
        UUID scopeId
) {}
