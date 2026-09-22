package com.pisethjavaschool.userservice.common.service;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PasswordPolicy {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 72;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*\\d.*");

    public Mono<Void> validate(String password) {
        if (password == null || password.isBlank()) {
            return Mono.error(new IllegalArgumentException("Password is required."));
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return Mono.error(new IllegalArgumentException("Password must be between 8 and 72 characters."));
        }

        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            return Mono.error(new IllegalArgumentException("Password must contain at least one uppercase letter."));
        }

        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            return Mono.error(new IllegalArgumentException("Password must contain at least one lowercase letter."));
        }

        if (!NUMBER_PATTERN.matcher(password).matches()) {
            return Mono.error(new IllegalArgumentException("Password must contain at least one number."));
        }

        return Mono.empty();
    }
}
