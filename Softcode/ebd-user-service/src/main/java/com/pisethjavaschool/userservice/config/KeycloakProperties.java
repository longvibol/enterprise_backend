package com.pisethjavaschool.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(String baseUrl, String realm, Admin admin, Login login) {
    public record Admin(String clientId, String clientSecret) {}
    public record Login(String clientId, String clientSecret) {}
}
