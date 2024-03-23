package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.responses.IUser;

@Component
public class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    public IUser map(User user) {
        IUser response = new IUser()
                .setId(user.getId()).setRole(roleMapper.map(user.getRole()))
                .setEmail(user.getEmail()).setPhoneNumber(user.getPhoneNumber())
                .setPhoneVerifiedAt(user.getPhoneVerifiedAt()).setGender(user.getGender())
                .setEnabled(user.getEnabled()).setFirstTimeLogin(user.getFirstTimeLogin())
                .setFullName(user.getFullName()).setEmailVerifiedAt(user.getEmailVerifiedAt())
                .setCreatedAt(user.getCreatedAt()).setUpdatedAt(user.getUpdatedAt()).setDeletedAt(user.getDeletedAt());

        return response;
    }
}
