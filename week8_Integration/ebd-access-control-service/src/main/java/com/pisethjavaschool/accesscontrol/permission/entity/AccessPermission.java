package com.pisethjavaschool.accesscontrol.permission.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.accesscontrol.permission.enums.AccessModule;
import com.pisethjavaschool.platform.common.audit.AuditableEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("access_permission")
public class AccessPermission extends AuditableEntity {
    @Id
    private UUID id;
    private String code;
    private String name;
    private String description;
    private AccessModule module;
    private Boolean active;
}
