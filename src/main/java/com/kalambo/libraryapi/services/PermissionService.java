package com.kalambo.libraryapi.services;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermission;

public interface PermissionService {
    void create(PermissionDto permissionDto);

    IPage<IPermission> getAll(Pageable pageable);
}
