package com.pisethjavaschool.userservice.authentication.keycloak.impl;

import java.net.URI;
import java.util.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.pisethjavaschool.userservice.config.KeycloakProperties;
import com.pisethjavaschool.userservice.authentication.keycloak.KeycloakTokenResponse;
import com.pisethjavaschool.userservice.authentication.keycloak.KeycloakUserClient;
import com.pisethjavaschool.userservice.common.exception.KeycloakIntegrationException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KeycloakUserClientImpl implements KeycloakUserClient {
    private final WebClient.Builder webClientBuilder;
    private final KeycloakProperties properties;

    @Override
    public Mono<String> createUser(String username, String email, String firstName, String lastName, String password, boolean enabled) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("username", username);
        body.put("email", email);
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("enabled", enabled);
        body.put("credentials", List.of(Map.of("type", "password", "value", password, "temporary", false)));
        return adminToken()
                .flatMap(token -> client().post().uri(adminUsersUri()).headers(h -> h.setBearerAuth(token)).contentType(MediaType.APPLICATION_JSON).bodyValue(body).exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        URI location = response.headers().asHttpHeaders().getLocation();
                        if (location == null) {
                            return Mono.error(new KeycloakIntegrationException("Keycloak did not return user location."));
                        }
                        String path = location.getPath();
                        return Mono.just(path.substring(path.lastIndexOf('/') + 1));
                    }
                    return response.bodyToMono(String.class).defaultIfEmpty("").flatMap(error -> Mono.error(new KeycloakIntegrationException("Create Keycloak user failed: " + error)));
                }));
    }

    @Override
    public Mono<Void> updateUser(String keycloakUserId, String email, String firstName, String lastName, boolean enabled) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("email", email);
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("enabled", enabled);
        return adminToken().flatMap(token -> client().put().uri(adminUsersUri() + "/" + keycloakUserId).headers(h -> h.setBearerAuth(token)).contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().bodyToMono(Void.class));
    }

    @Override
    public Mono<Void> resetPassword(String keycloakUserId, String password) {
        Map<String, Object> body = Map.of("type", "password", "value", password, "temporary", false);
        return adminToken().flatMap(token -> client().put().uri(adminUsersUri() + "/" + keycloakUserId + "/reset-password").headers(h -> h.setBearerAuth(token)).contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().bodyToMono(Void.class));
    }

    @Override
    public Mono<Void> disableUser(String keycloakUserId) {
        return updateUser(keycloakUserId, null, null, null, false);
    }

    @Override
    public Mono<KeycloakTokenResponse> login(String username, String password) {
        return client().post().uri(tokenUri()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", properties.login().clientId())
                        .with("client_secret", properties.login().clientSecret())
                        .with("username", username)
                        .with("password", password))
                .retrieve().bodyToMono(KeycloakTokenResponse.class);
    }

    private Mono<String> adminToken() {
        return client().post().uri(tokenUri()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", properties.admin().clientId())
                        .with("client_secret", properties.admin().clientSecret()))
                .retrieve().bodyToMono(Map.class).map(map -> String.valueOf(map.get("access_token")));
    }
    
    @Override
    public Mono<KeycloakTokenResponse> refreshToken(String refreshToken) {
        return client().post()
                .uri(tokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                        .with("client_id", properties.login().clientId())
                        .with("client_secret", properties.login().clientSecret())
                        .with("refresh_token", refreshToken))
                .retrieve()
                .bodyToMono(KeycloakTokenResponse.class);
    }

    private WebClient client() {
        return webClientBuilder.baseUrl(properties.baseUrl()).build();
    }

    private String tokenUri() {
        return "/realms/" + properties.realm() + "/protocol/openid-connect/token";
    }

    private String adminUsersUri() {
        return "/admin/realms/" + properties.realm() + "/users";
    }
}
