package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.responses.IRole;

@Component
public class RoleMapper {
    public IRole map(Role role) {
        IRole response = new IRole().setId(role.getId())
                .setName(role.getName()).setActive(role.getActive())
                .setUpdatedAt(role.getUpdatedAt()).setDeletedAt(role.getDeletedAt())
                .setDescription(role.getDescription()).setCreatedAt(role.getCreatedAt());

        return response;
    }
}
