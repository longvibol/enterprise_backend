
package com.pisethjavaschool.property.dto;

import java.util.UUID;

public record PropertyImageResponse(
        UUID mediaId,
        Integer sortOrder,
        Boolean thumbnail
) {
}
