package com.pisethjavaschool.userservice.businessstaff.facade;

import com.pisethjavaschool.userservice.businessstaff.dto.CreateBusinessStaffRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface BusinessStaffManagementFacade {
    Mono<UserResponse> createBusinessStaff(CreateBusinessStaffRequest request);
}
