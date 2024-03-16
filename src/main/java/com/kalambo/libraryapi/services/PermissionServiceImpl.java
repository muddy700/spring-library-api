package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermission;
import com.kalambo.libraryapi.utilities.PageMapper;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PageMapper<Permission, IPermission> pageMapper;

    @Override
    public void create(PermissionDto permissionDto) {
        permissionRepository.save(permissionDto.toEntity());
    }

    @Override
    public IPage<IPermission> getAll(Pageable pageable) {
        Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return pageMapper.paginate(permissionPage);
    }
}
