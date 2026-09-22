package com.pisethjavaschool.userservice.user.dto;

import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.Gender;
import com.pisethjavaschool.userservice.user.enums.RegistrationStatus;
import com.pisethjavaschool.userservice.user.enums.UserType;
import java.time.Instant;
import java.util.UUID;

public record UserResponse(

        UUID id,

        String keycloakUserId,

        UserType userType,

        String countryCode,

        String phoneNumber,

        String email,

        String username,

        AccountStatus accountStatus,

        RegistrationStatus registrationStatus,

        String firstName,

        String lastName,

        Gender gender,

        UUID photoMediaId,

        Instant createdAt,

        Instant updatedAt
) {
}
