package com.kalambo.libraryapi.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.dtos.UpdateUserDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.events.UserCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.repositories.RoleRepository;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IUser;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PageMapper<User, IUser> pageMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public IUser create(UserDto userDto) {
        checkDuplication(userDto.getEmail());
        User user = userRepository.save(userDto.toEntity().setRole(getRoleInfo(userDto.getRoleId())));

        publisher.publishEvent(new UserCreatedEvent(user));
        return userMapper.map(user);
    }

    @Override
    public IPage<IUser> getAll(Pageable pageable) {
        return pageMapper.paginate(userRepository.findAll(pageable));
    }

    @Override
    public IUser getById(UUID userId) {
        return userMapper.map(getEntity(userId));
    }

    @Override
    public IUser update(UpdateUserDto payload) {
        return userMapper.map(userRepository.save(copyNonNullValues(payload)));
    }

    @Override
    public void delete(UUID userId) {
        userRepository.delete(getEntity(userId));
    }

    @Override
    public User getEntity(UUID userId) {
        String errorMessage = "No user found with ID: " + userId;

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    private User copyNonNullValues(UpdateUserDto payload) {
        // Get existing user info
        User userInfo = getEntity(payload.getId());

        // Append all updatable fields here.
        if (payload.getEmail() != null) {
            checkDuplication(payload.getEmail());
            userInfo.setEmail(payload.getEmail());
        }

        if (payload.getPhoneNumber() != null)
            userInfo.setPhoneNumber(payload.getPhoneNumber());

        if (payload.getFullName() != null)
            userInfo.setFullName(payload.getFullName());

        if (payload.getGender() != null)
            userInfo.setGender(payload.getGender());

        if (payload.getEnabled() != null)
            userInfo.setEnabled(payload.getEnabled());

        if (payload.getRoleId() != null)
            userInfo.setRole(getRoleInfo(payload.getRoleId()));

        return userInfo;
    }

    private void checkDuplication(String email) {
        String errorMessage = "User with email: " + email + ", already exist";

        if (userRepository.findByEmail(email).isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }

    private Role getRoleInfo(UUID roleId) {
        return roleService.getEntity(roleId);
    }
}