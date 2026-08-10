package com.pisethjavaschool.userservice.businessowner.controller;

import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.businessowner.facade.BusinessOwnerRegistrationFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class BusinessOwnerRegistrationController {

    private final BusinessOwnerRegistrationFacade businessOwnerRegistrationFacade;

    @PostMapping("/business-owner")
    public Mono<UserResponse> registerBusinessOwner(
            @Valid @RequestBody BusinessOwnerRegistrationRequest request
    ) {
        return businessOwnerRegistrationFacade.registerBusinessOwner(request);
    }
}
