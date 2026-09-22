package com.pisethjavaschool.userservice.businessowner.facade.impl;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.enums.AccessRoleCode;
import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerClient;
import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import com.pisethjavaschool.platform.propertyowner.client.dto.OwnerType;
import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;
import com.pisethjavaschool.userservice.businessowner.enums.BusinessType;
import com.pisethjavaschool.userservice.businessowner.facade.BusinessOwnerRegistrationFacade;
import com.pisethjavaschool.userservice.businessowner.mapper.BusinessOwnerRegistrationMapper;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.user.service.UserCommandService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BusinessOwnerRegistrationFacadeImpl implements BusinessOwnerRegistrationFacade {
    
	
	private final UserCommandService userCommandService;
    private final BusinessOwnerRegistrationMapper mapper;
    private final PropertyOwnerClient propertyOwnerClient;
    private final AccessControlClient accessControlClient;

    @Override
    public Mono<UserResponse> registerBusinessOwner(BusinessOwnerRegistrationRequest request) {
        return userCommandService.create(mapper.toCreateUserRequest(request))
                .flatMap(user -> createOwnerAndAssignRole(user, request));
    }

    private Mono<UserResponse> createOwnerAndAssignRole(UserResponse user, BusinessOwnerRegistrationRequest request) {
        
    	CreatePropertyOwnerCommand command = new CreatePropertyOwnerCommand(
                user.id(),
                OwnerType.valueOf(request.ownerType().name()),
                request.firstName().trim() + " " + request.lastName().trim(),
                request.businessName(),
                null,
                null,
                request.countryCode() + request.phoneNumber(),
                request.email(),
                request.telegram());

        return propertyOwnerClient.createOwner(command)
                .flatMap(organizationId -> accessControlClient.assignRole(
                        user.id(),
                        ownerRole(request.businessType()),
                        organizationId))
                .thenReturn(user);
    }

    private AccessRoleCode ownerRole(BusinessType businessType) {
        return switch (businessType) {
            case RESTAURANT -> AccessRoleCode.RESTAURANT_OWNER;
            case KTV -> AccessRoleCode.KTV_OWNER;
        };
    }
    
	
}
