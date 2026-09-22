package com.pisethjavaschool.userservice.user.repository;

import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.enums.UserType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAccountSearchRepository {

    Flux<UserAccount> search(UserType userType, String keyword, long offset, int limit);

    Mono<Long> count(UserType userType, String keyword);
}