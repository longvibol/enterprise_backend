package com.pisethjavaschool.accesscontrol.rolepermission.dto;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record ReplaceRolePermissionsRequest(
        @NotEmpty Set<UUID> permissionIds
) {}
