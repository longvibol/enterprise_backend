package com.pisethjavaschool.userservice.user.repository;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.enums.UserType;

public interface UserAccountRepository extends ReactiveCrudRepository<UserAccount, UUID> {
	Mono<UserAccount> findByUsernameIgnoreCaseAndUserType(String username, UserType userType);

	Mono<Boolean> existsByUsernameIgnoreCase(String username);

	Mono<UserAccount> findByKeycloakUserId(String keycloakUserId);

}
