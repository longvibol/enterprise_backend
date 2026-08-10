package com.pisethjavaschool.platform.common.pagination;

import java.util.List;

public final class PageUtils {
	private PageUtils() {
	}

	public static int offset(int page, int size) {
		return Math.max(page, 0) * Math.max(size, 1);
	}

	public static int totalPages(long total, int size) {
		return size <= 0 ? 0 : (int) Math.ceil((double) total / size);
	}

	public static <T> PageResponse<T> toPageResponse(List<T> items, long total, int page, int size) {
		return new PageResponse<>(items, total, page, size, totalPages(total, size));
	}
}
