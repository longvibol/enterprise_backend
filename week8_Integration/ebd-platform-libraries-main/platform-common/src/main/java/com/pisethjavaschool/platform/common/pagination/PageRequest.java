package com.pisethjavaschool.platform.common.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PageRequest(@Min(0) Integer page, @Min(1) @Max(200) Integer size) {
	public int pageOrDefault() {
		return page == null ? 0 : page;
	}

	public int sizeOrDefault() {
		return size == null ? 20 : size;
	}
}
