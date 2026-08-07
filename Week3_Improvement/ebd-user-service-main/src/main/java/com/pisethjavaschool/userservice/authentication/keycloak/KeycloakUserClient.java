package com.pisethjavaschool.userservice.authentication.keycloak;

import reactor.core.publisher.Mono;

public interface KeycloakUserClient {
	Mono<String> createUser(String username, String email, String firstName, String lastName, String password,
			boolean enabled);

	Mono<Void> updateUser(String keycloakUserId, String email, String firstName, String lastName, boolean enabled);

	Mono<Void> resetPassword(String keycloakUserId, String password);

	Mono<Void> disableUser(String keycloakUserId);

	Mono<KeycloakTokenResponse> login(String username, String password);

	Mono<KeycloakTokenResponse> refreshToken(String refreshToken);
}
