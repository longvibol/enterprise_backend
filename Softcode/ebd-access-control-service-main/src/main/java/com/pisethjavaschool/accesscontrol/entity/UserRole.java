package com.pisethjavaschool.accesscontrol.entity;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.accesscontrol.common.audit.AuditableEntity;
import com.pisethjavaschool.accesscontrol.enums.ScopeType;

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
@Table("user_role")
public class UserRole extends AuditableEntity {
	@Id
	private UUID id;
	@Column("user_id")
	private UUID userId;
	@Column("role_id")
	private UUID roleId;
	@Column("scope_type")
	private ScopeType scopeType;
	@Column("scope_id")
	private UUID scopeId;
	private Boolean active;
}
