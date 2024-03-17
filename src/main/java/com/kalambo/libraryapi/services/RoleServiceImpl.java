package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.dtos.UpdateRoleDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.RoleMapper;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IRole;
import com.kalambo.libraryapi.utilities.PageMapper;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PageMapper<Role, IRole> pageMapper;

    @Override
    public IRole create(RoleDto roleDto) {
        Role role = roleRepository.save(roleDto.toEntity());

        return roleMapper.map(role);
    }

    @Override
    public IPage<IRole> getAll(Pageable pageable) {
        return pageMapper.paginate(roleRepository.findAll(pageable));
    }

    @Override
    public IRole getById(UUID roleId) {
        String errorMessage = "No role found with ID: " + roleId;

        Role roleInfo = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return roleMapper.map(roleInfo);
    }

    @Override
    public IRole update(UpdateRoleDto role) {
        return roleMapper.map(roleRepository.save(copyNonNullValues(role)));
    }

    @Override
    public void delete(UUID roleId) {
        // Ensure role is present or throw 404
        getById(roleId);

        // TODO: Delete all relational data here (if any)
        roleRepository.deleteById(roleId);
    }

    private Role copyNonNullValues(UpdateRoleDto payload) {
        // Ensure role is present or throw 404
        getById(payload.getId());

        // Get existing role info
        Role roleInfo = roleRepository.findById(payload.getId()).get();

        // Append all updatable fields here.
        if (payload.getName() != null)
            roleInfo.setName(payload.getName());

        if (payload.getDescription() != null)
            roleInfo.setDescription(payload.getDescription());

        if (payload.getActive() != null)
            roleInfo.setActive(payload.getActive());

        return roleInfo;
    }
}
