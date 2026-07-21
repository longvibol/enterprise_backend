package com.pisethjavaschool.accesscontrol.common.audit;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AuditableEntity {

    @CreatedDate
    private Instant createdAt;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedDate
    private Instant updatedAt;

    @LastModifiedBy
    private UUID updatedBy;
}
