package com.pisethjavaschool.propertyowner.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOwnerDocumentRequest(
        @NotBlank @Size(max = 50) String documentTypeCode,
        @Size(max = 100) String documentNumber,
        @NotNull
        UUID mediaId
) {}
