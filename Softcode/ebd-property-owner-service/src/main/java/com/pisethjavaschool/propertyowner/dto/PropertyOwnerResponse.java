package com.pisethjavaschool.propertyowner.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

public record PropertyOwnerResponse(
        UUID id,
        UUID userId,
        OwnerType ownerType,
        String displayName,
        String businessName,
        String businessRegistrationNumber,
        String taxNumber,
        String phoneNumber,
        String email,
        String telegram,
        OwnerVerificationStatus verificationStatus,
        Instant verifiedAt,
        UUID verifiedBy,
        String rejectionReason,
        Boolean active,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy
) {}
