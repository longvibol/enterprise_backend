package com.pisethjavaschool.userservice.common.service;

import org.springframework.stereotype.Component;

@Component
public class UsernameResolver {
    public String resolve(String email, String countryCode, String phoneNumber) {
        if (email != null && !email.isBlank()) {
            return email.trim().toLowerCase();
        }
        if (countryCode == null || countryCode.isBlank() || phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Either email or countryCode and phoneNumber is required.");
        }
        return countryCode.trim() + phoneNumber.trim();
    }
}
