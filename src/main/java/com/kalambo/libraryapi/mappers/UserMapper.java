package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.responses.IUser;
import com.kalambo.libraryapi.responses.IUserV2;
import com.kalambo.libraryapi.responses.IUserV3;

@Component
public class UserMapper {
    @Autowired
    private RoleMapper roleMapper;

    public IUser map(User user) {
        IUser response = new IUser()
                .setId(user.getId()).setRole(roleMapper.map(user.getRole()))
                .setEmail(user.getEmail()).setPhoneNumber(user.getPhoneNumber())
                .setPhoneVerifiedAt(user.getPhoneVerifiedAt()).setGender(user.getGender())
                .setEnabled(user.getEnabled()).setFullName(user.getFullName())
                .setEmailVerifiedAt(user.getEmailVerifiedAt())
                .setCreatedAt(user.getCreatedAt()).setUpdatedAt(user.getUpdatedAt()).setDeletedAt(user.getDeletedAt());

        return response;
    }

    public IUserV2 mapToV2(User user) {
        IUserV2 response = new IUserV2().setId(user.getId())
                .setEmail(user.getEmail()).setFullName(user.getFullName())
                .setGender(user.getGender()).setEnabled(user.getEnabled())
                .setPhoneNumber(user.getPhoneNumber()).setRoleName(user.getRole().getName());

        return response;
    }

    public IUserV3 mapToV3(User user) {
        IUserV3 response = new IUserV3().setId(user.getId())
                .setEmail(user.getEmail()).setFullName(user.getFullName());

        return response;
    }
}
