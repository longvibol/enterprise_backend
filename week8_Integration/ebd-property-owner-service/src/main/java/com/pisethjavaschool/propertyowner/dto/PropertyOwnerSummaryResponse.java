package com.pisethjavaschool.propertyowner.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

public record PropertyOwnerSummaryResponse(
        UUID id,
        UUID userId,
        OwnerType ownerType,
        String displayName,
        String businessName,
        String phoneNumber,
        String email,
        OwnerVerificationStatus verificationStatus,
        Boolean active,
        Instant createdAt
) {}
