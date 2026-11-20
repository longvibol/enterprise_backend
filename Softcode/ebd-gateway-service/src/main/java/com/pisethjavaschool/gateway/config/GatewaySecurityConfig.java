package com.pisethjavaschool.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration(proxyBeanMethods = false)
public class GatewaySecurityConfig {

    @Bean
    SecurityWebFilterChain gatewaySecurityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .pathMatchers("/api/v1/access-control/internal/**").denyAll()
                        .pathMatchers("/api/v1/access-control/check-permission").denyAll()
                        .pathMatchers("/api/v1/property-owners/internal/**").denyAll()
                        .pathMatchers("/api/v1/users/internal/**", "/api/v1/users/users/internal/**").denyAll()
                        .pathMatchers(HttpMethod.POST,
                                "/api/v1/users/register/business-owner",
                                "/api/v1/users/auth/login",
                                "/api/v1/users/auth/refresh-token").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }
}