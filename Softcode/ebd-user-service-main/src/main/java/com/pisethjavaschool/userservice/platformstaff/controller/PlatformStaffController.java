package com.pisethjavaschool.userservice.platformstaff.controller;

import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.platformstaff.dto.CreatePlatformStaffRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.platformstaff.facade.PlatformStaffManagementFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/platform-staff")
public class PlatformStaffController {

    private final PlatformStaffManagementFacade platformStaffManagementFacade;

    @PostMapping
    public Mono<UserResponse> create(
            @Valid @RequestBody CreatePlatformStaffRequest request
    ) {
        return platformStaffManagementFacade.createPlatformStaff(request, UserType.PLATFORM_STAFF);
    }

    @PostMapping("/system-admin")
    public Mono<UserResponse> createSystemAdmin(
            @Valid @RequestBody CreatePlatformStaffRequest request
    ) {
        return platformStaffManagementFacade.createPlatformStaff(request, UserType.SYSTEM_ADMIN);
    }
}
