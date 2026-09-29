package com.pisethjavaschool.property.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.service.PropertyFactory;

@Service
public class PropertyFactoryImpl implements PropertyFactory {

    @Override
    public Property createDraft(UUID ownerId, CreatePropertyRequest request) {
        return Property.builder()
                .ownerId(ownerId)
                .name(request.name().trim())
                .businessType(request.businessType())
                .description(request.description())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .telegram(request.telegram())
                .facebookPage(request.facebookPage())
                .provinceCode(request.provinceCode())
                .districtCode(request.districtCode())
                .communeCode(request.communeCode())
                .villageCode(request.villageCode())
                .streetAddress(request.streetAddress())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .thumbnailMediaId(request.thumbnailMediaId())
                .status(PropertyStatus.DRAFT)
                .active(true)
                .build();
    }
}