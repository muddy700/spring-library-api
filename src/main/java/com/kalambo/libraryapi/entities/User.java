package com.kalambo.libraryapi.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kalambo.libraryapi.entities.abstracts.BaseEntity;
import com.kalambo.libraryapi.enums.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
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

@Table(name = "users", indexes = {
        @Index(name = "email_index", columnList = "email", unique = true),
        @Index(name = "deleted_index", columnList = "deleted_at"),
        @Index(name = "phone_index", columnList = "phoneNumber", unique = true),
        @Index(name = "name_index", columnList = "fullName", unique = true),
        @Index(name = "role_index", columnList = "role_id"),
})

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")

public class User extends BaseEntity implements UserDetails {
    @Column(length = 40, unique = true, nullable = false)
    private String email;

    @Column(length = 12, unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    private Date emailVerifiedAt;
    private Date phoneVerifiedAt;

    @Column(length = 50, unique = true, nullable = false)
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(nullable = false)
    private Boolean enabled = Boolean.TRUE;

    // Relationships
    @ManyToOne(optional = false)
    private Role role;

    // For security implementations
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        role.getPermissions()
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getAction())));

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
