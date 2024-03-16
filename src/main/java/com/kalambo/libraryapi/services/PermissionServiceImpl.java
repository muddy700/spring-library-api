package com.kalambo.libraryapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.mappers.PermissionMapper;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermission;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void create(PermissionDto permissionDto) {
        permissionRepository.save(permissionDto.toEntity());
    }

    @Override
    public IPage<IPermission> getAll(Pageable pageable) {
        Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return formatResponse(permissionPage);
    }

    private IPage<IPermission> formatResponse(Page<Permission> permissionPage) {
        List<IPermission> contents = new ArrayList<IPermission>(permissionPage.getSize());

        for (Permission permission : permissionPage.getContent()) {
            contents.add(permissionMapper.map(permission));
        }

        IPage<IPermission> response = new IPage<IPermission>().setItems(contents)
                .setTotalPages(permissionPage.getTotalPages()).setCurrentPage(permissionPage.getNumber())
                .setTotalItems(permissionPage.getTotalElements()).setCurrentSize(permissionPage.getSize());

        return response;
    }
}
