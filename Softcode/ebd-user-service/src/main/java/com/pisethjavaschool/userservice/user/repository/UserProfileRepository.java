package com.pisethjavaschool.userservice.user.repository;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import com.pisethjavaschool.userservice.user.entity.UserProfile;
import com.pisethjavaschool.userservice.user.enums.UserType;

public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, UUID> {
    Mono<UserProfile> findByUserAccountId(UUID userAccountId);

}
