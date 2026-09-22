package com.pisethjavaschool.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration")
public record IntegrationProperties(String accessControlBaseUrl, String businessOwnerBaseUrl) {}
