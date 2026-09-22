package com.pisethjavaschool.propertyowner.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

public record OwnerVerificationHistoryResponse(
        UUID id,
        UUID ownerId,
        OwnerVerificationStatus oldStatus,
        OwnerVerificationStatus newStatus,
        String reason,
        UUID reviewedBy,
        Instant reviewedAt
) {}
