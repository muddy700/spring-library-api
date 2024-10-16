package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.PermissionDto;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IPermissionV2;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PageMapper<Permission, IPermissionV2> pageMapper;

    @Override
    public void create(PermissionDto permissionDto) {
        permissionRepository.save(permissionDto.toEntity());
    }

    @Override
    public IPage<IPermissionV2> getAll(Pageable pageable) {
        return pageMapper.paginate(permissionRepository.findAll(pageable));
    }
}
