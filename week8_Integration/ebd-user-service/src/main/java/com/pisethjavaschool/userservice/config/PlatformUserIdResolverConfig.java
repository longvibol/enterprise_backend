package com.pisethjavaschool.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pisethjavaschool.platform.security.PlatformUserIdResolver;
import com.pisethjavaschool.userservice.user.repository.UserAccountRepository;
@Configuration
public class PlatformUserIdResolverConfig {
	
    @Bean
    public PlatformUserIdResolver platformUserIdResolver(UserAccountRepository repository) {
        return keycloakUserId -> repository.findByKeycloakUserId(keycloakUserId.toString())
                .map(account -> account.getId());
    }
} 