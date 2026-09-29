
package com.pisethjavaschool.property.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.pisethjavaschool.platform.common.audit.AuditableEntity;

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
@Table("property_image")
public class PropertyImage extends AuditableEntity {

    @Id
    private UUID id;

    @Column("property_id")
    private UUID propertyId;

    @Column("media_id")
    private UUID mediaId;

    @Column("sort_order")
    private Integer sortOrder;

    private Boolean thumbnail;
}
