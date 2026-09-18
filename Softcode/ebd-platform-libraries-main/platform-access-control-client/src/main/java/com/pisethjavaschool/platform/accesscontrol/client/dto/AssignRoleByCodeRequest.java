package com.pisethjavaschool.platform.accesscontrol.client.dto;

import java.util.UUID;

import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;

public record AssignRoleByCodeRequest(AccessRoleCode roleCode, UUID scopeId) {
}