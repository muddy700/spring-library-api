package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.dtos.UpdateUserDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IUser;
import com.kalambo.libraryapi.responses.IUserV2;

public interface UserService {
    IUser create(UserDto userDto);

    IPage<IUserV2> getAll(Pageable pageable);

    IUser getById(UUID userId);

    IUser update(UpdateUserDto userDto);

    void delete(UUID userId);

    User getEntity(UUID userId);
}
