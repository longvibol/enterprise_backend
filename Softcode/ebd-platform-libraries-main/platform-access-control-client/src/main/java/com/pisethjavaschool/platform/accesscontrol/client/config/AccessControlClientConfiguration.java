package com.pisethjavaschool.platform.accesscontrol.client.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlProvisioningClient;
import com.pisethjavaschool.platform.accesscontrol.client.AccessControlQueryClient;
import com.pisethjavaschool.platform.accesscontrol.client.impl.DefaultAccessControlProvisioningClient;
import com.pisethjavaschool.platform.accesscontrol.client.impl.DefaultAccessControlQueryClient;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;
/*
@AutoConfiguration
@EnableConfigurationProperties(AccessControlClientProperties.class)
public class AccessControlClientConfiguration {
	
	@Bean
    public AccessControlClient accessControlClient(
            ObjectProvider<WebClient.Builder> webClientBuilderProvider,
            AccessControlClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        WebClient.Builder builder = webClientBuilderProvider.getIfAvailable(WebClient::builder);
        return new DefaultAccessControlClient(builder, properties, tokenProvider);
    }
    
}
*/

@AutoConfiguration
@EnableConfigurationProperties(AccessControlClientProperties.class)
public class AccessControlClientConfiguration {
    @Bean
    AccessControlQueryClient accessControlQueryClient(ObjectProvider<WebClient.Builder> builders,
            AccessControlClientProperties properties) {
        return new DefaultAccessControlQueryClient(builders.getIfAvailable(WebClient::builder), properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "platform.clients.access-control.provisioning", name = "enabled", havingValue = "true")
    AccessControlProvisioningClient accessControlProvisioningClient(ObjectProvider<WebClient.Builder> builders,
            AccessControlClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        return new DefaultAccessControlProvisioningClient(builders.getIfAvailable(WebClient::builder), properties, tokenProvider);
    }
}
