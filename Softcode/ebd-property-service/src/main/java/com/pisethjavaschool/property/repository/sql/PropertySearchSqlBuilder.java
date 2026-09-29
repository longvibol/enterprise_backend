package com.pisethjavaschool.property.repository.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.property.enums.PropertyStatus;

@Component
public class PropertySearchSqlBuilder {

    public PropertySearchSql build(UUID ownerId, PropertyStatus status, Boolean active, String keyword) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<SqlBindValue> bindings = new ArrayList<>();

        if (ownerId != null) {
            where.append(" AND owner_id = :ownerId ");
            bindings.add(new SqlBindValue("ownerId", ownerId));
        }
        if (status != null) {
            where.append(" AND status = :status ");
            bindings.add(new SqlBindValue("status", status.name()));
        }
        if (active != null) {
            where.append(" AND active = :active ");
            bindings.add(new SqlBindValue("active", active));
        }
        if (keyword != null && !keyword.isBlank()) {
            where.append(" AND (LOWER(name) LIKE :keyword OR LOWER(business_type) LIKE :keyword) ");
            bindings.add(new SqlBindValue("keyword", "%" + keyword.toLowerCase().trim() + "%"));
        }

        String select = "SELECT * FROM property" + where + " ORDER BY created_at DESC LIMIT :limit OFFSET :offset";
        String count = "SELECT COUNT(*) FROM property" + where;
        return new PropertySearchSql(select, count, bindings);
    }
}
