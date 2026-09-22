package com.pisethjavaschool.accesscontrol.rolepermission.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table("role_permission")
public class RolePermission {
    @Id
    private UUID id;
    @Column("role_id")
    private UUID roleId;
    @Column("permission_id")
    private UUID permissionId;
}
