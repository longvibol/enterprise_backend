package com.pisethjavaschool.property.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import com.pisethjavaschool.property.enums.BusinessType;
import jakarta.validation.constraints.*;

public record UpdatePropertyRequest(
        @NotBlank @Size(max = 150) String name,
        @NotNull BusinessType businessType,
        @Size(max = 3000) String description,
        @Size(max = 30) String phoneNumber,
        @Email @Size(max = 150) String email,
        @Size(max = 100) String telegram,
        @Size(max = 255) String facebookPage,
        @NotBlank @Size(max = 20) String provinceCode,
        @NotBlank @Size(max = 20) String districtCode,
        @Size(max = 20) String communeCode,
        @Size(max = 20) String villageCode,
        @Size(max = 255) String streetAddress,
        BigDecimal latitude,
        BigDecimal longitude,
        UUID thumbnailMediaId,
        @Size(max = 30) List<UUID> galleryMediaIds
) {}
