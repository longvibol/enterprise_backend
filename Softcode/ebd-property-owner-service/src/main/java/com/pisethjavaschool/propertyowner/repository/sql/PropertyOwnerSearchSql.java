package com.pisethjavaschool.propertyowner.repository.sql;

import java.util.List;

public record PropertyOwnerSearchSql(String selectSql, String countSql, List<SqlBindValue> bindings) {}
