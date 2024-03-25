package com.kalambo.libraryapi.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.responses.IPermission;
import com.kalambo.libraryapi.responses.IRole;

@Component
public class RoleMapper {
    @Autowired
    private PermissionMapper permissionMapper;

    public IRole map(Role role) {
        IRole response = new IRole().setId(role.getId())
                .setPermissions(mapPermissions(role.getPermissions()))
                .setName(role.getName()).setActive(role.getActive())
                .setUpdatedAt(role.getUpdatedAt()).setDeletedAt(role.getDeletedAt())
                .setDescription(role.getDescription()).setCreatedAt(role.getCreatedAt());

        return response;
    }

    private List<IPermission> mapPermissions(Set<Permission> permissions) {
        List<IPermission> result = new ArrayList<IPermission>(permissions.size());
        permissions.forEach(permission -> result.add(permissionMapper.map(permission)));

        return result;
    }
}
