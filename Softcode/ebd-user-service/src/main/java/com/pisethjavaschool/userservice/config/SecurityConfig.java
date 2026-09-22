package com.pisethjavaschool.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/health", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .pathMatchers(
                        		"/api/v1/auth/**",
                        		"/api/v1/register/**",
                        		"/api/v1/platform-staff/**" // Temporary only
                        ).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }
}
