package com.pisethjavaschool.propertyowner.facade.impl;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.pisethjavaschool.platform.security.CurrentUserReader;
import com.pisethjavaschool.propertyowner.entity.OwnerVerificationHistory;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;
import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;
import com.pisethjavaschool.propertyowner.exception.InvalidOwnerStatusException;
import com.pisethjavaschool.propertyowner.facade.ChangePropertyOwnerStatusFacade;
import com.pisethjavaschool.propertyowner.repository.OwnerVerificationHistoryRepository;
import com.pisethjavaschool.propertyowner.service.OwnerDocumentReviewService;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerReader;
import com.pisethjavaschool.propertyowner.service.PropertyOwnerWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangePropertyOwnerStatusFacadeImpl implements ChangePropertyOwnerStatusFacade {
    private final CurrentUserReader currentUserReader;
    private final PropertyOwnerReader reader;
    private final PropertyOwnerWriter writer;
    private final OwnerDocumentReviewService ownerDocumentReviewService;
    private final OwnerVerificationHistoryRepository historyRepository;

    @Override @Transactional
    public Mono<Void> submit(UUID id) {
        return currentUserReader.getCurrentUserId()
                .flatMap(userId -> reader.getOwnedOwner(id, userId))
                .flatMap(owner -> changeStatus(owner, OwnerVerificationStatus.PENDING_REVIEW, null, owner.getUserId(), OwnerVerificationStatus.DRAFT, OwnerVerificationStatus.REJECTED))
                .doOnSuccess(unused -> log.info("Business owner submitted for verification: ownerId={}", id));
    }

    @Override @Transactional
    public Mono<Void> verify(UUID id) {
        return currentUserReader.getCurrentUserId()
                .flatMap(reviewerId -> reader.getById(id)
                        .flatMap(owner -> changeStatus(owner, OwnerVerificationStatus.VERIFIED, null, reviewerId, OwnerVerificationStatus.PENDING_REVIEW)
                                .then(ownerDocumentReviewService.verifyPendingDocuments(owner.getId()))))
                .doOnSuccess(unused -> log.info("Business owner verified: ownerId={}", id));
    }

    @Override @Transactional
    public Mono<Void> reject(UUID id, String reason) {
        return currentUserReader.getCurrentUserId()
                .flatMap(reviewerId -> reader.getById(id)
                        .flatMap(owner -> changeStatus(owner, OwnerVerificationStatus.REJECTED, reason, reviewerId, OwnerVerificationStatus.PENDING_REVIEW)));
    }

    @Override @Transactional
    public Mono<Void> suspend(UUID id) {
        return currentUserReader.getCurrentUserId()
                .flatMap(reviewerId -> reader.getById(id)
                        .flatMap(owner -> changeStatus(owner, OwnerVerificationStatus.SUSPENDED, null, reviewerId, OwnerVerificationStatus.VERIFIED)));
    }

    @Override @Transactional
    public Mono<Void> activate(UUID id) {
        return currentUserReader.getCurrentUserId().flatMap(userId -> reader.getOwnedOwner(id, userId))
                .flatMap(owner -> { owner.setActive(true); return writer.save(owner).then(); });
    }

    @Override @Transactional
    public Mono<Void> deactivate(UUID id) {
        return currentUserReader.getCurrentUserId().flatMap(userId -> reader.getOwnedOwner(id, userId))
                .flatMap(owner -> { owner.setActive(false); return writer.save(owner).then(); });
    }

    private Mono<Void> changeStatus(PropertyOwner owner, OwnerVerificationStatus target, String reason, UUID reviewedBy, OwnerVerificationStatus... allowedStatuses) {
        if (!Arrays.asList(allowedStatuses).contains(owner.getVerificationStatus())) {
            return Mono.error(new InvalidOwnerStatusException("Cannot change owner status from " + owner.getVerificationStatus() + " to " + target));
        }
        OwnerVerificationStatus oldStatus = owner.getVerificationStatus();
        owner.setVerificationStatus(target);
        owner.setRejectionReason(reason);
        if (target == OwnerVerificationStatus.VERIFIED) { owner.setVerifiedAt(Instant.now()); owner.setVerifiedBy(reviewedBy); }
        OwnerVerificationHistory history = OwnerVerificationHistory.builder().ownerId(owner.getId()).oldStatus(oldStatus).newStatus(target).reason(reason).reviewedBy(reviewedBy).reviewedAt(Instant.now()).build();
        return writer.save(owner).then(historyRepository.save(history)).then();
    }
}
