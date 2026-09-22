package com.pisethjavaschool.propertyowner.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.platform.common.audit.AuditableEntity;
import com.pisethjavaschool.propertyowner.enums.OwnerDocumentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("owner_document")
public class OwnerDocument extends AuditableEntity {
	@Id
	private UUID id;

	@Column("owner_id")
	private UUID ownerId;

	@Column("document_type_code")
	private String documentTypeCode;

	@Column("document_number")
	private String documentNumber;

	@Column("media_id")
	private UUID mediaId;

	private OwnerDocumentStatus status;

	@Column("rejection_reason")
	private String rejectionReason;

	private Boolean active;
}
