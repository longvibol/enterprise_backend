package com.pisethjavaschool.userservice.user.entity;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import com.pisethjavaschool.userservice.user.enums.Gender;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_profile")
public class UserProfile extends AuditableEntity {
    @Id
    private UUID id;
    private UUID userAccountId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private UUID photoMediaId;
}
