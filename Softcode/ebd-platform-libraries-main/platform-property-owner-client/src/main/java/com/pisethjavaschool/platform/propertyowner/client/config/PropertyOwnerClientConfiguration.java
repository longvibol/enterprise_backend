package com.pisethjavaschool.platform.propertyowner.client.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerClient;
import com.pisethjavaschool.platform.propertyowner.client.impl.DefaultPropertyOwnerClient;
@Configuration
@EnableConfigurationProperties(PropertyOwnerClientProperties.class)
public class PropertyOwnerClientConfiguration {

    @Bean
    public PropertyOwnerClient propertyOwnerClient(
            ObjectProvider<WebClient.Builder> webClientBuilderProvider,
            PropertyOwnerClientProperties properties) {

        WebClient.Builder builder = webClientBuilderProvider.getIfAvailable(WebClient::builder);
        return new DefaultPropertyOwnerClient(builder, properties);
    }
}