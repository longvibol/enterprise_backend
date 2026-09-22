package com.pisethjavaschool.userservice.businessstaff.mapper;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.pisethjavaschool.userservice.businessstaff.entity.BusinessStaffProfile;
import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.businessstaff.dto.CreateBusinessStaffRequest;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;

@Mapper(componentModel = "spring")
public interface BusinessStaffMapper {

    @Mapping(target = "userType", expression = "java(businessStaffType())")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "photoMediaId", ignore = true)
    CreateUserRequest toCreateUserRequest(CreateBusinessStaffRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userAccountId", source = "userAccountId")
    @Mapping(target = "status", expression = "java(activeStatus())")
    BusinessStaffProfile toProfile(CreateBusinessStaffRequest request, UUID userAccountId);

    default UserType businessStaffType() {
        return UserType.BUSINESS_STAFF;
    }

    default AccountStatus activeStatus() {
        return AccountStatus.ACTIVE;
    }
}
