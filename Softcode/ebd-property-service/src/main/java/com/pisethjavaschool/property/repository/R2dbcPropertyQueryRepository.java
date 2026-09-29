package com.pisethjavaschool.property.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.pisethjavaschool.platform.common.pagination.PageResult;
import com.pisethjavaschool.property.entity.Property;
import com.pisethjavaschool.property.enums.PropertyStatus;
import com.pisethjavaschool.property.repository.mapper.PropertyRowMapper;
import com.pisethjavaschool.property.repository.sql.PropertySearchSql;
import com.pisethjavaschool.property.repository.sql.PropertySearchSqlBuilder;
import com.pisethjavaschool.property.repository.sql.SqlBindValue;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class R2dbcPropertyQueryRepository implements PropertyQueryRepository {
    private final DatabaseClient databaseClient;
    private final PropertySearchSqlBuilder sqlBuilder;
    private final PropertyRowMapper rowMapper;

    @Override
    public Mono<PageResult<Property>> search(UUID ownerId, PropertyStatus status, Boolean active, String keyword, int page, int size) {
        long offset = (long) Math.max(page, 0) * Math.max(size, 1);
        PropertySearchSql sql = sqlBuilder.build(ownerId, status, active, keyword);

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

    private DatabaseClient.GenericExecuteSpec bind(DatabaseClient.GenericExecuteSpec spec, PropertySearchSql sql) {
        DatabaseClient.GenericExecuteSpec result = spec;
        for (SqlBindValue bind : sql.bindings()) {
            result = result.bind(bind.name(), bind.value());
        }
        return result;
    }


    @Override
    public Mono<PageResult<Property>> searchDelegated(
            Set<UUID> propertyIds,
            PropertyStatus status,
            Boolean active,
            String keyword,
            int page,
            int size
    ) {
        if (propertyIds == null || propertyIds.isEmpty()) {
            return Mono.just(new PageResult<>(java.util.List.of(), 0L));
        }

        long offset =
                (long) Math.max(page, 0) * Math.max(size, 1);

        StringBuilder where = new StringBuilder(
                " WHERE id = ANY(:propertyIds)"
        );

        if (status != null) {
            where.append(" AND status = :status");
        }

        if (active != null) {
            where.append(" AND active = :active");
        }

        if (keyword != null && !keyword.isBlank()) {
            where.append(
                    " AND LOWER(name) LIKE LOWER(:keyword)"
            );
        }

        String selectSql =
                "SELECT * FROM property"
                        + where
                        + " ORDER BY created_at DESC"
                        + " LIMIT :limit OFFSET :offset";

        String countSql =
                "SELECT COUNT(*) FROM property" + where;

        UUID[] ids = propertyIds.toArray(UUID[]::new);

        DatabaseClient.GenericExecuteSpec selectSpec =
                databaseClient
                        .sql(selectSql)
                        .bind("propertyIds", ids)
                        .bind("limit", size)
                        .bind("offset", offset);

        DatabaseClient.GenericExecuteSpec countSpec =
                databaseClient
                        .sql(countSql)
                        .bind("propertyIds", ids);

        if (status != null) {
            selectSpec =
                    selectSpec.bind("status", status.name());
            countSpec =
                    countSpec.bind("status", status.name());
        }

        if (active != null) {
            selectSpec =
                    selectSpec.bind("active", active);
            countSpec =
                    countSpec.bind("active", active);
        }

        if (keyword != null && !keyword.isBlank()) {
            String pattern = "%" + keyword.trim() + "%";
            selectSpec =
                    selectSpec.bind("keyword", pattern);
            countSpec =
                    countSpec.bind("keyword", pattern);
        }

        return Mono.zip(
                        selectSpec
                                .map((row, metadata) ->
                                        rowMapper.apply(row)
                                )
                                .all()
                                .collectList(),
                        countSpec
                                .map((row, metadata) -> {
                                    Number count =
                                            row.get(0, Number.class);
                                    return count == null
                                            ? 0L
                                            : count.longValue();
                                })
                                .one()
                                .defaultIfEmpty(0L)
                )
                .map(tuple ->
                        new PageResult<>(
                                tuple.getT1(),
                                tuple.getT2()
                        )
                );
    }

}
