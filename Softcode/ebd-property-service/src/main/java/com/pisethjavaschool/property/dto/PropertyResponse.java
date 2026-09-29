package com.pisethjavaschool.property.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.enums.PropertyStatus;

public record PropertyResponse(UUID id, UUID ownerId, String name, BusinessType businessType, String description, String phoneNumber, String email, String telegram, String facebookPage, String provinceCode, String districtCode, String communeCode, String villageCode, String streetAddress, BigDecimal latitude, BigDecimal longitude, UUID thumbnailMediaId, PropertyStatus status, Boolean active, String rejectionReason, List<PropertyImageResponse> images, Instant createdAt, UUID createdBy, Instant updatedAt, UUID updatedBy) {}
