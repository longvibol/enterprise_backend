package com.pisethjavaschool.userservice.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.platform.common.pagination.PageResponse;
import com.pisethjavaschool.platform.r2dbc.SqlPageSupport;
import com.pisethjavaschool.userservice.common.exception.NotFoundException;
import com.pisethjavaschool.userservice.user.dto.UserIdentityResponse;
import com.pisethjavaschool.userservice.user.dto.UserResponse;
import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.entity.UserProfile;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.user.mapper.UserMapper;
import com.pisethjavaschool.userservice.user.repository.UserAccountRepository;
import com.pisethjavaschool.userservice.user.repository.UserAccountSearchRepository;
import com.pisethjavaschool.userservice.user.repository.UserProfileRepository;
import com.pisethjavaschool.userservice.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserAccountRepository accountRepository;
    private final UserProfileRepository profileRepository;
    private final UserMapper mapper;
    private final UserAccountSearchRepository accountSearchRepository;

    @Override
    public Mono<UserResponse> findById(UUID id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .flatMap(this::toResponse);
    }
    
    @Override
    public Mono<UserIdentityResponse> findIdentityByKeycloakUserId(UUID keycloakUserId) {
        return accountRepository.findByKeycloakUserId(keycloakUserId.toString())
                .switchIfEmpty(Mono.error(new NotFoundException("User account not found.")))
                .map(mapper::toIdentityResponse);
    }

    /*
    @Override
    public Mono<PageResponse<UserResponse>> search(UserType userType, String keyword, int page, int size) {
        int safePage = SqlPageSupport.safePage(page);
        int safeSize = SqlPageSupport.safeSize(size);

        return accountRepository.findAll()
                .filter(account -> userType == null || account.getUserType() == userType)
                .filter(account -> matchesKeyword(account, keyword))
                .flatMap(this::toResponse)
                .collectList()
                .map(items -> {
                    int totalPages = (int) Math.ceil((double) items.size() / safeSize);
                    return new PageResponse<>(
                            items.stream().skip((long) safePage * safeSize).limit(safeSize).toList(),
                            items.size(),
                            safePage,
                            safeSize,
                            totalPages
                    );
                });
    }
    */
    
    @Override
    public Mono<PageResponse<UserResponse>> search(UserType userType, String keyword, int page, int size) {
        int safePage = SqlPageSupport.safePage(page);
        int safeSize = SqlPageSupport.safeSize(size);
        long offset = SqlPageSupport.offset(safePage, safeSize);

        Mono<List<UserResponse>> items = accountSearchRepository
                .search(userType, keyword, offset, safeSize)
                .flatMapSequential(this::toResponse)
                .collectList();

        Mono<Long> totalElements = accountSearchRepository.count(userType, keyword);

        return Mono.zip(items, totalElements)
                .map(result -> {
                    long total = result.getT2();
                    int totalPages = (int) ((total + safeSize - 1) / safeSize);

                    return new PageResponse<>(
                            result.getT1(),
                            total,
                            safePage,
                            safeSize,
                            totalPages);
                });
    }

    private boolean matchesKeyword(UserAccount account, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }
        String lower = keyword.toLowerCase();
        return contains(account.getEmail(), lower) || contains(account.getPhoneNumber(), lower) || contains(account.getUsername(), lower);
    }

    private boolean contains(String value, String keyword) {
        if (value == null) {
            return false;
        }
        return value.toLowerCase().contains(keyword);
    }

    private Mono<UserResponse> toResponse(UserAccount account) {
        return profileRepository.findByUserAccountId(account.getId())
                .defaultIfEmpty(UserProfile.builder().build())
                .map(profile -> mapper.toResponse(account, profile));
    }
}
