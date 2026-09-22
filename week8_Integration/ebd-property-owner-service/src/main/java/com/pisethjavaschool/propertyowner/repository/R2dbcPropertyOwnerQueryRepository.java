package com.pisethjavaschool.propertyowner.repository;

import java.util.UUID;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.pisethjavaschool.platform.common.pagination.PageResult;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.repository.mapper.PropertyOwnerRowMapper;
import com.pisethjavaschool.propertyowner.repository.sql.PropertyOwnerSearchSql;
import com.pisethjavaschool.propertyowner.repository.sql.PropertyOwnerSearchSqlBuilder;
import com.pisethjavaschool.propertyowner.repository.sql.SqlBindValue;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcPropertyOwnerQueryRepository implements PropertyOwnerQueryRepository {
    private final DatabaseClient databaseClient;
    private final PropertyOwnerSearchSqlBuilder sqlBuilder;
    private final PropertyOwnerRowMapper rowMapper;

    @Override
    public Mono<PageResult<PropertyOwner>> search(UUID userId, OwnerType ownerType, OwnerVerificationStatus status, Boolean active, String keyword, int page, int size) {
        long offset = (long) Math.max(page, 0) * Math.max(size, 1);
        PropertyOwnerSearchSql sql = sqlBuilder.build(userId, ownerType, status, active, keyword);

        DatabaseClient.GenericExecuteSpec selectSpec = bind(databaseClient.sql(sql.selectSql()), sql)
                .bind("limit", size)
                .bind("offset", offset);
        DatabaseClient.GenericExecuteSpec countSpec = bind(databaseClient.sql(sql.countSql()), sql);

        return Mono.zip(
                selectSpec.map((row, metadata) -> rowMapper.apply(row)).all().collectList(),
                countSpec.map((row, metadata) -> {
                    Number count = row.get(0, Number.class);
                    return count == null ? 0L : count.longValue();
                }).one().defaultIfEmpty(0L)
        ).map(tuple -> new PageResult<>(tuple.getT1(), tuple.getT2()));
    }

    private DatabaseClient.GenericExecuteSpec bind(DatabaseClient.GenericExecuteSpec spec, PropertyOwnerSearchSql sql) {
        DatabaseClient.GenericExecuteSpec result = spec;
        for (SqlBindValue bind : sql.bindings()) {
            result = result.bind(bind.name(), bind.value());
        }
        return result;
    }
}
