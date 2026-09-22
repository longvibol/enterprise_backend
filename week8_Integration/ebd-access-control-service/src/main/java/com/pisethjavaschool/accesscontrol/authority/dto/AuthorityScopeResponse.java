package com.pisethjavaschool.accesscontrol.authority.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;

public record AuthorityScopeResponse(
        ScopeType scopeType,
        UUID scopeId
) {}
