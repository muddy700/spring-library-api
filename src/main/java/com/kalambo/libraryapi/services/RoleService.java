package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.dtos.UpdateRoleDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IRole;

public interface RoleService {
    IRole create(RoleDto roleDto);

    IPage<IRole> getAll(Pageable pageable);

    IRole getById(UUID roleId);

    IRole update(UpdateRoleDto roleDto);

    void delete(UUID roleId);
}
