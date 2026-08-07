package com.pisethjavaschool.userservice.authentication.dto;

import com.pisethjavaschool.userservice.user.dto.UserResponse;

public record LoginResponse(

        String accessToken,

        String refreshToken,

        Long expiresIn,

        String tokenType,

        UserResponse user
) {
}
