package com.pisethjavaschool.accesscontrol.dto;

import java.util.UUID;

import com.pisethjavaschool.accesscontrol.enums.ScopeType;

public record AuthorityScopeResponse(
        ScopeType scopeType,
        UUID scopeId
) {}
