package com.pisethjavaschool.property.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.pisethjavaschool.property.security.PlatformUserAuthorizationManager;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PlatformUserAuthorizationManager platformUserAuthorizationManager;

    @Bean
    SecurityWebFilterChain propertySecurityFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        // Deny administrative mutations until a scoped permission policy exists.
                        .pathMatchers("/api/v1/properties/admin/**").denyAll()
                        .pathMatchers("/api/v1/properties/**").access(platformUserAuthorizationManager)
                        .anyExchange().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }
}
