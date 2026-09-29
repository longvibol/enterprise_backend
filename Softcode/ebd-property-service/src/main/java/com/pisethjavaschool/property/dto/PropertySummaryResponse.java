package com.pisethjavaschool.property.dto;

import java.time.Instant;
import java.util.UUID;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.enums.PropertyStatus;

public record PropertySummaryResponse(UUID id, UUID ownerId, String name, BusinessType businessType, String provinceCode, String districtCode, UUID thumbnailMediaId, PropertyStatus status, Boolean active, Instant createdAt) {}
