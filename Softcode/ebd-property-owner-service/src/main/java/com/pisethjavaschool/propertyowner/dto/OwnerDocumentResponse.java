package com.pisethjavaschool.propertyowner.dto;

import java.time.Instant;
import java.util.UUID;

import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;

public record OwnerDocumentResponse(
        UUID id,
        UUID ownerId,
        String documentTypeCode,
        String documentNumber,
        UUID mediaId,
        OwnerDocumentStatus status,
        String rejectionReason,
        Boolean active,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy
) {}
