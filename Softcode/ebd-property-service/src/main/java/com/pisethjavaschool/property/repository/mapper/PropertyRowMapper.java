package com.pisethjavaschool.property.repository.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.enums.PropertyStatus;

import io.r2dbc.spi.Row;

@Component
public class PropertyRowMapper {

    public Property apply(Row row) {
        Property p = new Property();

        p.setId(row.get("id", UUID.class));
        p.setOwnerId(row.get("owner_id", UUID.class));

        p.setName(row.get("name", String.class));
        p.setBusinessType(BusinessType.valueOf(row.get("business_type", String.class)));
        p.setDescription(row.get("description", String.class));
        p.setPhoneNumber(row.get("phone_number", String.class));
        p.setEmail(row.get("email", String.class));
        p.setTelegram(row.get("telegram", String.class));
        p.setFacebookPage(row.get("facebook_page", String.class));

        p.setProvinceCode(row.get("province_code", String.class));
        p.setDistrictCode(row.get("district_code", String.class));
        p.setCommuneCode(row.get("commune_code", String.class));
        p.setVillageCode(row.get("village_code", String.class));

        p.setStreetAddress(row.get("street_address", String.class));

        p.setLatitude(row.get("latitude", BigDecimal.class));
        p.setLongitude(row.get("longitude", BigDecimal.class));
        p.setThumbnailMediaId(row.get("thumbnail_media_id", UUID.class));

        p.setStatus(PropertyStatus.valueOf(row.get("status", String.class)));
        p.setActive(row.get("active", Boolean.class));
        p.setRejectionReason(row.get("rejection_reason", String.class));

        p.setCreatedAt(row.get("created_at", Instant.class));
        p.setCreatedBy(row.get("created_by", UUID.class));
        p.setUpdatedAt(row.get("updated_at", Instant.class));
        p.setUpdatedBy(row.get("updated_by", UUID.class));

        return p;
    }
}