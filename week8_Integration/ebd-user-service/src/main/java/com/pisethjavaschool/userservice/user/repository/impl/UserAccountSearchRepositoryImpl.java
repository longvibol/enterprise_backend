package com.pisethjavaschool.userservice.user.repository.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pisethjavaschool.userservice.user.entity.UserAccount;
import com.pisethjavaschool.userservice.user.enums.UserType;
import com.pisethjavaschool.userservice.user.repository.UserAccountSearchRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserAccountSearchRepositoryImpl implements UserAccountSearchRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Flux<UserAccount> search(UserType userType, String keyword, long offset, int limit) {
        Query query = buildQuery(userType, keyword)
                .sort(Sort.by(
                        Sort.Order.desc("createdAt"),
                        Sort.Order.desc("id")))
                .offset(offset)
                .limit(limit);

        return template.select(query, UserAccount.class);
    }

    @Override
    public Mono<Long> count(UserType userType, String keyword) {
        return template.count(buildQuery(userType, keyword), UserAccount.class);
    }

    private Query buildQuery(UserType userType, String keyword) {
        Criteria criteria = buildCriteria(userType, keyword);
        return criteria == null ? Query.empty() : Query.query(criteria);
    }

    private Criteria buildCriteria(UserType userType, String keyword) {
        Criteria criteria = null;

        if (userType != null) {
            criteria = Criteria.where("userType").is(userType);
        }

        String normalizedKeyword = normalizeKeyword(keyword);
        if (normalizedKeyword != null) {
            String pattern = "%" + normalizedKeyword + "%";

            Criteria keywordCriteria = Criteria.where("email")
                    .like(pattern)
                    .ignoreCase(true)
                    .or("phoneNumber")
                    .like(pattern)
                    .ignoreCase(true)
                    .or("username")
                    .like(pattern)
                    .ignoreCase(true);

            criteria = criteria == null
                    ? keywordCriteria
                    : criteria.and(keywordCriteria);
        }

        return criteria;
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }

        String normalized = keyword.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}