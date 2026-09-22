package com.pisethjavaschool.userservice.user.dto;

import java.util.UUID;

public record UserIdentityResponse(
        UUID id,
        String keycloakUserId,
        String username,
        String email,
        String phoneNumber
) {
}