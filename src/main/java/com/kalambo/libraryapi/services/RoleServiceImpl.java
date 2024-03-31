package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.RoleDto;
import com.kalambo.libraryapi.dtos.UpdatePermissionDto;
import com.kalambo.libraryapi.dtos.UpdateRoleDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.RoleMapper;
import com.kalambo.libraryapi.repositories.PermissionRepository;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IRole;
import com.kalambo.libraryapi.responses.IRoleV2;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PageMapper<Role, IRoleV2> pageMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UUID create(RoleDto roleDto) {
        checkDuplication(roleDto.getName());
        Role rolePayload = roleDto.toEntity();

        if (!roleDto.getPermissionsIds().isEmpty())
            rolePayload.addPermissions(permissionRepository.findAllById(roleDto.getPermissionsIds()));

        return roleRepository.save(rolePayload).getId();
    }

    @Override
    public IPage<IRoleV2> getAll(Pageable pageable) {
        return pageMapper.paginate(roleRepository.findAll(pageable));
    }

    @Override
    public IRole getById(UUID roleId) {
        return roleMapper.map(getEntity(roleId));
    }

    @Override
    public Role getEntity(UUID roleId) {
        String errorMessage = "No role found with ID: " + roleId;

        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    @Override
    public void update(UpdateRoleDto role) {
        roleRepository.save(copyNonNullValues(role));
    }

    @Override
    public void managePermissions(UpdatePermissionDto payload) {
        Role role = getEntity(payload.getRoleId());

        if (payload.getRemovedPermissionsIds().isEmpty() && payload.getAddedPermissionsIds().isEmpty())
            return;

        if (!payload.getRemovedPermissionsIds().isEmpty())
            role.removePermissions(payload.getRemovedPermissionsIds());

        if (!payload.getAddedPermissionsIds().isEmpty())
            role.addPermissions(permissionRepository.findAllById(payload.getAddedPermissionsIds()));

        roleRepository.save(role);
    }

    @Override
    public void delete(UUID roleId) {
        roleRepository.delete(getEntity(roleId));
    }

    private void checkDuplication(String name) {
        String errorMessage = "Role with name: " + name + ", already exist";

        if (roleRepository.findByName(name).isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }

    private Role copyNonNullValues(UpdateRoleDto payload) {
        // Get existing role info
        Role roleInfo = getEntity(payload.getId());

        // Append all updatable fields here.
        if (payload.getName() != null) {
            checkDuplication(payload.getName());
            roleInfo.setName(payload.getName());
        }

        if (payload.getDescription() != null)
            roleInfo.setDescription(payload.getDescription());

        if (payload.getActive() != null)
            roleInfo.setActive(payload.getActive());

        return roleInfo;
    }
}
