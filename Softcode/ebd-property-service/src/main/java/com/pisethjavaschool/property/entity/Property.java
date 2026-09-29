package com.pisethjavaschool.property.entity;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.enums.PropertyStatus;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("property")
public class Property extends AuditableEntity {
    @Id private UUID id;
    @Column("owner_id") private UUID ownerId;
    private String name;
    @Column("business_type") private BusinessType businessType;
    private String description;
    @Column("phone_number") private String phoneNumber;
    private String email;
    private String telegram;
    @Column("facebook_page") private String facebookPage;
    @Column("province_code") private String provinceCode;
    @Column("district_code") private String districtCode;
    @Column("commune_code") private String communeCode;
    @Column("village_code") private String villageCode;
    @Column("street_address") private String streetAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column("thumbnail_media_id") private UUID thumbnailMediaId;
    private PropertyStatus status;
    private Boolean active;
    @Column("rejection_reason") private String rejectionReason;
}
