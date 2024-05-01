package com.kalambo.libraryapi.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.kalambo.libraryapi.entities.abstracts.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity

@Table(name = "roles", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true),
        @Index(name = "active_index", columnList = "active"),
        @Index(name = "deleted_index", columnList = "deleted_at")
})

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE roles SET deleted_at = NOW() WHERE id = ?")

public class Role extends BaseEntity {
    @Column(length = 25, unique = true, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    // Relationships
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission_mapping", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<Permission>();

    public void addPermissions(List<Permission> payload) {
        permissions.addAll(payload);
    }

    public void removePermissions(List<UUID> permissionsIds) {
        permissions.removeIf(permission -> permissionsIds.contains(permission.getId()));
    }
}
