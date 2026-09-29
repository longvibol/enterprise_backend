
package com.pisethjavaschool.property.service;

import java.util.List;
import java.util.UUID;

import reactor.core.publisher.Mono;

public interface PropertyImageWriter {

    Mono<Void> replaceForCreate(
            UUID propertyId,
            UUID thumbnailMediaId,
            List<UUID> galleryMediaIds
    );

    Mono<Void> replaceForUpdate(
            UUID propertyId,
            UUID thumbnailMediaId,
            List<UUID> galleryMediaIds
    );
}
