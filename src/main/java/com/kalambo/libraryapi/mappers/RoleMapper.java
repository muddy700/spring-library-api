package com.kalambo.libraryapi.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.responses.IPermissionV2;
import com.kalambo.libraryapi.responses.IRole;
import com.kalambo.libraryapi.responses.IRoleV2;
import com.kalambo.libraryapi.responses.IRoleV3;

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

    public IRoleV2 mapToV2(Role role) {
        IRoleV2 response = new IRoleV2().setId(role.getId())
                .setPermissionsCount(role.getPermissions().size())
                .setName(role.getName()).setActive(role.getActive());

        return response;
    }

    public IRoleV3 mapToV3(Role role) {
        return new IRoleV3().setId(role.getId()).setName(role.getName());
    }

    private List<IPermissionV2> mapPermissions(Set<Permission> permissions) {
        List<IPermissionV2> result = new ArrayList<IPermissionV2>(permissions.size());
        permissions.forEach(permission -> result.add(permissionMapper.mapToV2(permission)));

        return result;
    }
}
