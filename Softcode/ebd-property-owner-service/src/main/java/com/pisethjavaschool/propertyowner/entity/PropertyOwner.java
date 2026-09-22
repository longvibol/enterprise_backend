package com.pisethjavaschool.propertyowner.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("property_owner")
public class PropertyOwner extends AuditableEntity {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("owner_type")
    private OwnerType ownerType;

    @Column("display_name")
    private String displayName;

    @Column("business_name")
    private String businessName;

    @Column("business_registration_number")
    private String businessRegistrationNumber;

    @Column("tax_number")
    private String taxNumber;

    @Column("phone_number")
    private String phoneNumber;

    private String email;
    private String telegram;

    @Column("verification_status")
    private OwnerVerificationStatus verificationStatus;

    @Column("verified_at")
    private Instant verifiedAt;

    @Column("verified_by")
    private UUID verifiedBy;

    @Column("rejection_reason")
    private String rejectionReason;

    private Boolean active;
}
