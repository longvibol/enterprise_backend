package com.pisethjavaschool.platform.propertyowner.client.dto;

import java.util.UUID;

public record PropertyOwnerSummary(
        UUID id,
        UUID userId,
        OwnerType ownerType,
        String verificationStatus,
        Boolean active) {
}