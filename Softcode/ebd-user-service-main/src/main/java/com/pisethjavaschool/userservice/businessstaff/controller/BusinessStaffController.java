package com.pisethjavaschool.userservice.businessstaff.controller;

import com.pisethjavaschool.userservice.businessstaff.dto.CreateBusinessStaffRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.businessstaff.facade.BusinessStaffManagementFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/business-staff")
public class BusinessStaffController {

    private final BusinessStaffManagementFacade businessStaffManagementFacade;

    @PostMapping
    public Mono<UserResponse> create(
            @Valid @RequestBody CreateBusinessStaffRequest request
    ) {
        return businessStaffManagementFacade.createBusinessStaff(request);
    }
}
