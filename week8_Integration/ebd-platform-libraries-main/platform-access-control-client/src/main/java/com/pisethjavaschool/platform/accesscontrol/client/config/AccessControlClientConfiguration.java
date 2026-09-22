package com.pisethjavaschool.platform.accesscontrol.client.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlClient;
import com.pisethjavaschool.platform.accesscontrol.client.impl.DefaultAccessControlClient;

@Configuration
@EnableConfigurationProperties(AccessControlClientProperties.class)
public class AccessControlClientConfiguration {
    @Bean
    public AccessControlClient accessControlClient(
            WebClient.Builder webClientBuilder,
            AccessControlClientProperties properties) {
        return new DefaultAccessControlClient(webClientBuilder, properties);
    }
}