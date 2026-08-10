package com.pisethjavaschool.userservice.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.entity.UserProfile;
import com.pisethjavaschool.userservice.user.enums.AccountStatus;
import com.pisethjavaschool.userservice.user.enums.RegistrationStatus;
import com.pisethjavaschool.userservice.user.dto.CreateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UpdateUserRequest;
import com.pisethjavaschool.userservice.user.dto.UserIdentityResponse;
import com.pisethjavaschool.userservice.user.dto.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "accountStatus", source = "accountStatus")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    UserAccount toAccount(
            CreateUserRequest request,
            String username,
            String keycloakUserId,
            AccountStatus accountStatus,
            RegistrationStatus registrationStatus
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userAccountId", source = "userAccountId")
    @Mapping(target = "dateOfBirth", ignore = true)
    UserProfile toProfile(CreateUserRequest request, java.util.UUID userAccountId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keycloakUserId", ignore = true)
    @Mapping(target = "userType", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "registrationStatus", ignore = true)
    void updateAccount(UpdateUserRequest request, String username, @MappingTarget UserAccount account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userAccountId", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    void updateProfile(UpdateUserRequest request, @MappingTarget UserProfile profile);

    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "keycloakUserId", source = "account.keycloakUserId")
    @Mapping(target = "userType", source = "account.userType")
    @Mapping(target = "countryCode", source = "account.countryCode")
    @Mapping(target = "phoneNumber", source = "account.phoneNumber")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "username", source = "account.username")
    @Mapping(target = "accountStatus", source = "account.accountStatus")
    @Mapping(target = "registrationStatus", source = "account.registrationStatus")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "gender", source = "profile.gender")
    @Mapping(target = "photoMediaId", source = "profile.photoMediaId")
    @Mapping(target = "createdAt", source = "account.createdAt")
    @Mapping(target = "updatedAt", source = "account.updatedAt")
    UserResponse toResponse(UserAccount account, UserProfile profile);
    
    UserIdentityResponse toIdentityResponse(UserAccount account);
}
