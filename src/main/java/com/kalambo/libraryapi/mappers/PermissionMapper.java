package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.responses.IPermission;

@Component
public class PermissionMapper {
    public IPermission map(Permission permission) {
        IPermission response = new IPermission().setId(permission.getId())
                .setResourceName(permission.getResourceName()).setAction(permission.getAction())
                .setDescription(permission.getDescription()).setCreatedAt(permission.getCreatedAt());

        return response;
    }
}
