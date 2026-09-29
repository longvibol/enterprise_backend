
package com.pisethjavaschool.property.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pisethjavaschool.property.dto.PropertyImageResponse;
import com.pisethjavaschool.property.dto.PropertyResponse;
import com.pisethjavaschool.property.dto.PropertySummaryResponse;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.entity.PropertyImage;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertySummaryResponse toSummary(Property property);

    default PropertyImageResponse toImageResponse(PropertyImage image) {
        return new PropertyImageResponse(
                image.getMediaId(),
                image.getSortOrder(),
                image.getThumbnail()
        );
    }

    default PropertyResponse toResponse(
            Property property,
            List<PropertyImageResponse> images
    ) {
        return new PropertyResponse(
                property.getId(),
                property.getOwnerId(),
                property.getName(),
                property.getBusinessType(),
                property.getDescription(),
                property.getPhoneNumber(),
                property.getEmail(),
                property.getTelegram(),
                property.getFacebookPage(),
                property.getProvinceCode(),
                property.getDistrictCode(),
                property.getCommuneCode(),
                property.getVillageCode(),
                property.getStreetAddress(),
                property.getLatitude(),
                property.getLongitude(),
                property.getThumbnailMediaId(),
                property.getStatus(),
                property.getActive(),
                property.getRejectionReason(),
                images,
                property.getCreatedAt(),
                property.getCreatedBy(),
                property.getUpdatedAt(),
                property.getUpdatedBy()
        );
    }
}
