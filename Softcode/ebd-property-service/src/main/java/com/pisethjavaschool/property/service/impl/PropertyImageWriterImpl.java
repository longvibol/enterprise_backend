
package com.pisethjavaschool.property.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.pisethjavaschool.property.entity.PropertyImage;
import com.pisethjavaschool.property.repository.PropertyImageRepository;
import com.pisethjavaschool.property.service.PropertyImageWriter;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PropertyImageWriterImpl implements PropertyImageWriter {

    private final PropertyImageRepository imageRepository;

    @Override
    public Mono<Void> replaceForCreate(
            UUID propertyId,
            UUID thumbnailMediaId,
            List<UUID> galleryMediaIds
    ) {
        return saveImages(propertyId, thumbnailMediaId, galleryMediaIds);
    }

    @Override
    public Mono<Void> replaceForUpdate(
            UUID propertyId,
            UUID thumbnailMediaId,
            List<UUID> galleryMediaIds
    ) {
        return imageRepository.deleteByPropertyId(propertyId)
                .then(saveImages(propertyId, thumbnailMediaId, galleryMediaIds));
    }

    private Mono<Void> saveImages(
            UUID propertyId,
            UUID thumbnailMediaId,
            List<UUID> galleryMediaIds
    ) {
        Set<UUID> orderedMediaIds = new LinkedHashSet<>();

        if (thumbnailMediaId != null) {
            orderedMediaIds.add(thumbnailMediaId);
        }

        if (galleryMediaIds != null) {
            galleryMediaIds.stream()
                    .filter(id -> id != null)
                    .forEach(orderedMediaIds::add);
        }

        AtomicInteger index = new AtomicInteger();

        return Flux.fromIterable(orderedMediaIds)
                .map(mediaId -> PropertyImage.builder()
                        .propertyId(propertyId)
                        .mediaId(mediaId)
                        .sortOrder(index.getAndIncrement())
                        .thumbnail(mediaId.equals(thumbnailMediaId))
                        .build())
                .flatMap(imageRepository::save)
                .then();
    }
}
