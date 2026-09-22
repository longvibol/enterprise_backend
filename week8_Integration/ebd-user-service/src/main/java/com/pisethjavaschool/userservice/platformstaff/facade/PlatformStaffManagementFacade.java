package com.pisethjavaschool.userservice.platformstaff.facade;

import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.platformstaff.dto.CreatePlatformStaffRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface PlatformStaffManagementFacade {
    Mono<UserResponse> createPlatformStaff(CreatePlatformStaffRequest request, UserType staffType);
}
