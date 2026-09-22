package com.pisethjavaschool.platform.r2dbc;

public final class SqlPageSupport {

    private static final int DEFAULT_MAX_SIZE = 100;

    private SqlPageSupport() {
    }

    public static int safePage(int page) {
        return Math.max(page, 0);
    }

    public static int safeSize(int size) {
        return safeSize(size, DEFAULT_MAX_SIZE);
    }

    public static int safeSize(int size, int maxSize) {
        int safeMaxSize = Math.max(maxSize, 1);
        return Math.min(Math.max(size, 1), safeMaxSize);
    }

    public static long offset(int page, int size) {
        return (long) safePage(page) * safeSize(size);
    }

    public static String limitOffset() {
        return " LIMIT :limit OFFSET :offset ";
    }
}