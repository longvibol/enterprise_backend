package com.pisethjavaschool.userservice.platformstaff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.platformstaff.dto.CreatePlatformStaffRequest;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;

@Mapper(componentModel = "spring")
public interface PlatformStaffMapper {

    @Mapping(target = "userType", source = "staffType")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "photoMediaId", ignore = true)
    CreateUserRequest toCreateUserRequest(CreatePlatformStaffRequest request, UserType staffType);
}
