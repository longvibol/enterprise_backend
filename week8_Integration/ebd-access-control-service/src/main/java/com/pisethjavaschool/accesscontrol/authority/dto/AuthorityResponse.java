package com.pisethjavaschool.accesscontrol.authority.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AuthorityResponse(
        UUID userId,
        Set<String> roles,
        Set<String> permissions,
        List<AuthorityScopeResponse> scopes
) {}
