package com.pisethjavaschool.propertyowner.repository.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pisethjavaschool.propertyowner.enums.OwnerType;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

@Component
public class PropertyOwnerSearchSqlBuilder {

    public PropertyOwnerSearchSql build(UUID userId, OwnerType ownerType, OwnerVerificationStatus status, Boolean active, String keyword) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<SqlBindValue> bindings = new ArrayList<>();

        if (userId != null) {
            where.append(" AND user_id = :userId ");
            bindings.add(new SqlBindValue("userId", userId));
        }
        if (ownerType != null) {
            where.append(" AND owner_type = :ownerType ");
            bindings.add(new SqlBindValue("ownerType", ownerType.name()));
        }
        if (status != null) {
            where.append(" AND verification_status = :status ");
            bindings.add(new SqlBindValue("status", status.name()));
        }
        if (active != null) {
            where.append(" AND active = :active ");
            bindings.add(new SqlBindValue("active", active));
        }
        if (keyword != null && !keyword.isBlank()) {
            where.append(" AND (LOWER(display_name) LIKE :keyword OR LOWER(COALESCE(business_name, '')) LIKE :keyword OR LOWER(COALESCE(email, '')) LIKE :keyword) ");
            bindings.add(new SqlBindValue("keyword", "%" + keyword.toLowerCase().trim() + "%"));
        }

        String select = "SELECT * FROM property_owner" + where + " ORDER BY created_at DESC LIMIT :limit OFFSET :offset";
        String count = "SELECT COUNT(*) FROM property_owner" + where;
        return new PropertyOwnerSearchSql(select, count, bindings);
    }
}
