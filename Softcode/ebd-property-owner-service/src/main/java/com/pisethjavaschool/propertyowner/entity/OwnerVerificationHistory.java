package com.pisethjavaschool.propertyowner.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.propertyowner.enums.OwnerVerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("owner_verification_history")
public class OwnerVerificationHistory {
    @Id
    private UUID id;

    @Column("owner_id")
    private UUID ownerId;

    @Column("old_status")
    private OwnerVerificationStatus oldStatus;

    @Column("new_status")
    private OwnerVerificationStatus newStatus;

    private String reason;

    @Column("reviewed_by")
    private UUID reviewedBy;

    @Column("reviewed_at")
    private Instant reviewedAt;
}
