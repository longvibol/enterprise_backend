package com.pisethjavaschool.platform.propertyowner.client.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerProvisioningClient;
import com.pisethjavaschool.platform.propertyowner.client.PropertyOwnerQueryClient;
import com.pisethjavaschool.platform.propertyowner.client.impl.DefaultPropertyOwnerProvisioningClient;
import com.pisethjavaschool.platform.propertyowner.client.impl.DefaultPropertyOwnerQueryClient;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;

/*
@Configuration
@EnableConfigurationProperties(PropertyOwnerClientProperties.class)
public class PropertyOwnerClientConfiguration {

    @Bean
    public PropertyOwnerClient propertyOwnerClient(
            ObjectProvider<WebClient.Builder> webClientBuilderProvider,
            PropertyOwnerClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {

        WebClient.Builder builder = webClientBuilderProvider.getIfAvailable(WebClient::builder);
        return new DefaultPropertyOwnerClient(builder, properties, tokenProvider);
    }
}
*/
@AutoConfiguration
@EnableConfigurationProperties(PropertyOwnerClientProperties.class)
public class PropertyOwnerClientConfiguration {
    @Bean
    public PropertyOwnerQueryClient propertyOwnerQueryClient(
            ObjectProvider<WebClient.Builder> builders, PropertyOwnerClientProperties properties) {
        return new DefaultPropertyOwnerQueryClient(builders.getIfAvailable(WebClient::builder), properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "platform.clients.property-owner.provisioning", name = "enabled", havingValue = "true")
    public PropertyOwnerProvisioningClient propertyOwnerProvisioningClient(
            ObjectProvider<WebClient.Builder> builders, PropertyOwnerClientProperties properties,
            ProvisioningAccessTokenProvider tokenProvider) {
        return new DefaultPropertyOwnerProvisioningClient(builders.getIfAvailable(WebClient::builder), properties, tokenProvider);
    }
}
