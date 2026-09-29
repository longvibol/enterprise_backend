package com.pisethjavaschool.property.repository.sql;

import java.util.List;

public record PropertySearchSql(String selectSql, String countSql, List<SqlBindValue> bindings) {}
