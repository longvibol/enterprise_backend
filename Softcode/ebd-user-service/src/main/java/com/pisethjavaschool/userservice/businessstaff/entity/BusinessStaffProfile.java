package com.pisethjavaschool.userservice.businessstaff.entity;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("business_staff_profile")
public class BusinessStaffProfile extends AuditableEntity {
    @Id
    private UUID id;
    private UUID userAccountId;
    private UUID organizationId;
    private UUID businessId;
    private String position;
    private UUID invitedBy;
    private AccountStatus status;
}
