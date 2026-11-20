package com.pisethjavaschool.platform.accesscontrol.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.pisethjavaschool.platform.accesscontrol.client.AccessControlProvisioningClient;
import com.pisethjavaschool.platform.accesscontrol.client.AccessControlQueryClient;
import com.pisethjavaschool.platform.accesscontrol.client.impl.DefaultAccessControlProvisioningClient;
import com.pisethjavaschool.platform.accesscontrol.client.impl.DefaultAccessControlQueryClient;
import com.pisethjavaschool.platform.security.ProvisioningAccessTokenProvider;

@AutoConfiguration
@EnableConfigurationProperties(AccessControlClientProperties.class)
public class AccessControlClientConfiguration {
    /*
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
    */
	
	@Bean("accesscontrolDiscoveryWebClientBuilder")
    @LoadBalanced
    WebClient.Builder discoveryWebClientBuilder() { // NEW
        return WebClient.builder();
    }

    @Bean
    AccessControlQueryClient accessControlQueryClient(@Qualifier("accesscontrolDiscoveryWebClientBuilder") WebClient.Builder builder,
            AccessControlClientProperties properties) {
        return new DefaultAccessControlQueryClient(builder.clone(), properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "platform.clients.access-control.provisioning", name = "enabled", havingValue = "true")
    AccessControlProvisioningClient accessControlProvisioningClient(@Qualifier("accesscontrolDiscoveryWebClientBuilder") WebClient.Builder builder,
            AccessControlClientProperties properties, ProvisioningAccessTokenProvider tokenProvider) {
        return new DefaultAccessControlProvisioningClient(builder.clone(), properties, tokenProvider);
    }
    
}
