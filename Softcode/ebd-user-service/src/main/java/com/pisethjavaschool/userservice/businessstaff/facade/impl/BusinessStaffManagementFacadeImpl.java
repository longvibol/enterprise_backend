package com.pisethjavaschool.userservice.businessstaff.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;
import com.pisethjavaschool.userservice.businessstaff.dto.CreateBusinessStaffRequest;
import com.pisethjavaschool.userservice.businessstaff.entity.BusinessStaffProfile;
import com.pisethjavaschool.userservice.businessstaff.facade.BusinessStaffManagementFacade;
import com.pisethjavaschool.userservice.businessstaff.mapper.BusinessStaffMapper;
import com.pisethjavaschool.userservice.businessstaff.repository.BusinessStaffProfileRepository;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.user.service.UserCommandService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BusinessStaffManagementFacadeImpl implements BusinessStaffManagementFacade {
    private final UserCommandService userCommandService;
    private final BusinessStaffProfileRepository businessStaffProfileRepository;
    private final AccessControlClient accessControlClient;
    private final BusinessStaffMapper mapper;

    @Override
    @Transactional
    public Mono<UserResponse> createBusinessStaff(CreateBusinessStaffRequest request) {
        return userCommandService.create(mapper.toCreateUserRequest(request))
                .flatMap(user -> createBusinessStaffProfile(user, request)
                        .then(assignBusinessStaffRole(user, request))
                        .thenReturn(user));
    }

    private Mono<BusinessStaffProfile> createBusinessStaffProfile(UserResponse user, CreateBusinessStaffRequest request) {
        BusinessStaffProfile staffProfile = mapper.toProfile(request, user.id());
        return businessStaffProfileRepository.save(staffProfile);
    }

    private Mono<Void> assignBusinessStaffRole(UserResponse user, CreateBusinessStaffRequest request) {//
        return accessControlClient.assignRole( 
                user.id(),
                AccessRoleCode.BUSINESS_STAFF,
                request.organizationId());
    }
}
