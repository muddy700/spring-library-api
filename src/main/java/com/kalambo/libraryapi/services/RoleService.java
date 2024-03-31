package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.dtos.UpdatePermissionDto;
import com.kalambo.libraryapi.dtos.UpdateRoleDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IRole;
import com.kalambo.libraryapi.responses.IRoleV2;

public interface RoleService {
    IRole create(RoleDto roleDto);

    IPage<IRoleV2> getAll(Pageable pageable);

    IRole getById(UUID roleId);

    Role getEntity(UUID roleId);

    IRole update(UpdateRoleDto roleDto);

    IRole managePermissions(UpdatePermissionDto permissions);

    void delete(UUID roleId);
}
