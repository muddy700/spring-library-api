package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.responses.IPermission;
import com.kalambo.libraryapi.responses.IPermissionV2;
import com.kalambo.libraryapi.responses.IPermissionV3;

@Component
public class PermissionMapper {
    public IPermission map(Permission permission) {
        IPermission response = new IPermission().setId(permission.getId())
                .setResourceName(permission.getResourceName()).setAction(permission.getAction())
                .setDescription(permission.getDescription()).setCreatedAt(permission.getCreatedAt());

        return response;
    }

    public IPermissionV2 mapToV2(Permission permission) {
        return new IPermissionV2().setId(permission.getId())
                .setResourceName(permission.getResourceName()).setDescription(permission.getDescription());
    }

    public IPermissionV3 mapToV3(Permission permission) {
        return new IPermissionV3().setId(permission.getId()).setDescription(permission.getDescription());
    }
}
