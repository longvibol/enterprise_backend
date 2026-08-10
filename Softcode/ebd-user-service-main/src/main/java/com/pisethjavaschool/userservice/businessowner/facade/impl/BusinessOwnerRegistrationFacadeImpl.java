package com.pisethjavaschool.userservice.businessowner.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.businessowner.facade.BusinessOwnerRegistrationFacade;
import com.pisethjavaschool.userservice.businessowner.mapper.BusinessOwnerRegistrationMapper;
import com.pisethjavaschool.userservice.user.service.UserCommandService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BusinessOwnerRegistrationFacadeImpl implements BusinessOwnerRegistrationFacade {
    private final UserCommandService userCommandService;
    private final BusinessOwnerRegistrationMapper mapper;

    @Override
    @Transactional
    public Mono<UserResponse> registerBusinessOwner(BusinessOwnerRegistrationRequest request) {
        return userCommandService.create(mapper.toCreateUserRequest(request));
    }
}
