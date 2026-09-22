package com.pisethjavaschool.userservice.common.exception;

import com.pisethjavaschool.platform.exception.BadRequestException;

public class KeycloakIntegrationException extends BadRequestException {
    public KeycloakIntegrationException(String message) {
        super("KEYCLOAK_INTEGRATION_ERROR", message);
    }
}
