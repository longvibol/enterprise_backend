package com.pisethjavaschool.userservice.user.entity;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.RegistrationStatus;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_account")
public class UserAccount extends AuditableEntity {
    @Id
    private UUID id;
    private String keycloakUserId;
    private UserType userType;
    private String countryCode;
    private String phoneNumber;
    private String email;
    private String username;
    private AccountStatus accountStatus;
    private RegistrationStatus registrationStatus;
}
