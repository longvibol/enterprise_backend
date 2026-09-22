package com.pisethjavaschool.userservice.businessowner.facade;

import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface BusinessOwnerRegistrationFacade {
    Mono<UserResponse> registerBusinessOwner(BusinessOwnerRegistrationRequest request);
}
