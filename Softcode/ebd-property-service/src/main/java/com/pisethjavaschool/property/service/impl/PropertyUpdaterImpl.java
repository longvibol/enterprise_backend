package com.pisethjavaschool.property.service.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.service.PropertyUpdater;

@Service
public class PropertyUpdaterImpl implements PropertyUpdater {

    @Override
    public Property apply(Property property, UpdatePropertyRequest request) {
        property.setName(request.name().trim());
        property.setDescription(request.description());
        property.setPhoneNumber(request.phoneNumber());
        property.setEmail(request.email());
        property.setTelegram(request.telegram());
        property.setFacebookPage(request.facebookPage());

        property.setProvinceCode(request.provinceCode());
        property.setDistrictCode(request.districtCode());
        property.setCommuneCode(request.communeCode());
        property.setVillageCode(request.villageCode());

        property.setStreetAddress(request.streetAddress());
        property.setLatitude(request.latitude());
        property.setLongitude(request.longitude());
        property.setThumbnailMediaId(request.thumbnailMediaId());

        if (property.getStatus() == PropertyStatus.REJECTED) {
            property.setStatus(PropertyStatus.DRAFT);
            property.setRejectionReason(null);
        }

        return property;
    }
}