package com.pisethjavaschool.userservice.user.service;

import java.util.UUID;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.user.dto.*;
import com.pisethjavaschool.platform.common.pagination.PageResponse;
import reactor.core.publisher.Mono;

public interface UserQueryService {
    Mono<UserResponse> findById(UUID id);
    Mono<UserIdentityResponse> findIdentityByKeycloakUserId(UUID keycloakUserId);
    Mono<PageResponse<UserResponse>> search(UserType userType, String keyword, int page, int size);
}
