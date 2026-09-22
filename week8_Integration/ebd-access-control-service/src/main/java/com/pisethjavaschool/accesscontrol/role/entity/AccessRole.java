package com.pisethjavaschool.accesscontrol.role.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.accesscontrol.role.enums.RoleType;
import com.pisethjavaschool.accesscontrol.common.enums.ScopeType;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("access_role")
public class AccessRole extends AuditableEntity {
    @Id
    private UUID id;
    private String code;
    private String name;
    private String description;
    @Column("role_type")
    private RoleType roleType;
    @Column("scope_type")
    private ScopeType scopeType;
    @Column("system_role")
    private Boolean systemRole;
    private Boolean active;
}
