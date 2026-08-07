package com.pisethjavaschool.userservice.platformstaff.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.platformstaff.dto.CreatePlatformStaffRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.platformstaff.facade.PlatformStaffManagementFacade;
import com.pisethjavaschool.userservice.platformstaff.mapper.PlatformStaffMapper;
import com.pisethjavaschool.userservice.common.client.AccessControlClient;
import com.pisethjavaschool.userservice.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlatformStaffManagementFacadeImpl implements PlatformStaffManagementFacade {
    private final UserCommandService userCommandService;
    private final AccessControlClient accessControlClient;
    private final PlatformStaffMapper mapper;

    @Override
    @Transactional
    public Mono<UserResponse> createPlatformStaff(CreatePlatformStaffRequest request, UserType staffType) {
        if (staffType != UserType.PLATFORM_STAFF && staffType != UserType.SYSTEM_ADMIN) {
            return Mono.error(new IllegalArgumentException("Only PLATFORM_STAFF or SYSTEM_ADMIN is allowed here."));
        }

        return userCommandService.create(mapper.toCreateUserRequest(request, staffType))
                .flatMap(user -> accessControlClient.assignDefaultRole(user.id(), staffType, null)
                        .thenReturn(user));
    }
}
