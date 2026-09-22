package com.pisethjavaschool.propertyowner.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.platform.common.audit.AuditableEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table("owner_document_type")
public class OwnerDocumentType extends AuditableEntity {
    @Id
    private UUID id;
    private String code;
    private String name;
    private String description;
    private Boolean active;
    @Column("sort_order")
    private Integer sortOrder;
}
