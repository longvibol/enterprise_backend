package com.pisethjavaschool.userservice.config;

import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;

@Configuration
public class ProvisioningOAuth2Configuration {
    @Bean
    ReactiveOAuth2AuthorizedClientManager provisioningAuthorizedClientManager(
            ReactiveClientRegistrationRepository registrations, ReactiveOAuth2AuthorizedClientService clients) {
        var manager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(registrations, clients);
        manager.setAuthorizedClientProvider(ReactiveOAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build());
        return manager;
    }
    @Bean
    ProvisioningAccessTokenProvider provisioningAccessTokenProvider(ReactiveOAuth2AuthorizedClientManager manager) {
        return new ProvisioningAccessTokenProvider(manager, "user-service-provisioning");
    }
}
