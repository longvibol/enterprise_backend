package com.pisethjavaschool.userservice.businessowner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;
import com.pisethjavaschool.userservice.businessowner.dto.BusinessOwnerRegistrationRequest;

@Mapper(componentModel = "spring")
public interface BusinessOwnerRegistrationMapper {

    @Mapping(target = "userType", expression = "java(businessOwnerType())")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "photoMediaId", ignore = true)
    CreateUserRequest toCreateUserRequest(BusinessOwnerRegistrationRequest request);

    default UserType businessOwnerType() {
        return UserType.BUSINESS_OWNER;
    }
}
