package com.pisethjavaschool.propertyowner.repository.mapper;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

import io.r2dbc.spi.Row;

@Component
public class PropertyOwnerRowMapper {

    public PropertyOwner apply(Row row) {
        PropertyOwner owner = new PropertyOwner();
        owner.setId(row.get("id", UUID.class));
        owner.setUserId(row.get("user_id", UUID.class));
        owner.setOwnerType(OwnerType.valueOf(row.get("owner_type", String.class)));
        owner.setDisplayName(row.get("display_name", String.class));
        owner.setBusinessName(row.get("business_name", String.class));
        owner.setBusinessRegistrationNumber(row.get("business_registration_number", String.class));
        owner.setTaxNumber(row.get("tax_number", String.class));
        owner.setPhoneNumber(row.get("phone_number", String.class));
        owner.setEmail(row.get("email", String.class));
        owner.setTelegram(row.get("telegram", String.class));
        owner.setVerificationStatus(OwnerVerificationStatus.valueOf(row.get("verification_status", String.class)));
        owner.setVerifiedAt(row.get("verified_at", Instant.class));
        owner.setVerifiedBy(row.get("verified_by", UUID.class));
        owner.setRejectionReason(row.get("rejection_reason", String.class));
        owner.setActive(row.get("active", Boolean.class));
        owner.setCreatedAt(row.get("created_at", Instant.class));
        owner.setCreatedBy(row.get("created_by", UUID.class));
        owner.setUpdatedAt(row.get("updated_at", Instant.class));
        owner.setUpdatedBy(row.get("updated_by", UUID.class));
        return owner;
    }
}
