package com.kalambo.libraryapi.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.UserDto;
import com.github.javafaker.Faker;
import com.kalambo.libraryapi.dtos.UpdateUserDto;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.events.UserCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IUser;
import com.kalambo.libraryapi.responses.IUserV2;
import com.kalambo.libraryapi.utilities.GlobalUtil;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PageMapper<User, IUserV2> pageMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GlobalUtil globalUtil;

    private String generatedPassword;

    @Override
    public UUID create(UserDto userDto) {
        checkDuplication(userDto.getEmail());

        User payload = userDto.toEntity()
                .setRole(getRoleInfo(userDto.getRoleId())).setPassword(generatePassword());

        User createdUser = userRepository.save(payload);
        trackRequest("create", createdUser, userDto.toString());
        publisher.publishEvent(new UserCreatedEvent(createdUser.setPassword(generatedPassword)));

        return createdUser.getId();
    }

    @Override
    public IUser getByToken(Authentication auth) {
        return userMapper.map((User) auth.getPrincipal());
    }

    @Override
    public IPage<IUserV2> getAll(Pageable pageable) {
        return pageMapper.paginate(userRepository.findAll(pageable));
    }

    @Override
    public IUser getById(UUID userId) {
        return userMapper.map(getEntity(userId));
    }

    @Override
    public void update(UpdateUserDto payload) {
        User userInfoBeforeUpdate = getEntity(payload.getId());
        
        userRepository.save(copyNonNullValues(payload));
        trackRequest("update", userInfoBeforeUpdate, payload.toString());
    }

    @Override
    public void delete(UUID userId) {
        User targetUser = getEntity(userId);

        userRepository.delete(targetUser);
        trackRequest("delete", targetUser, null);
    }

    @Override
    public User getEntity(UUID userId) {
        String errorMessage = "No user found with ID: " + userId;

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    @Override
    public User getEntity(String email) {
        String errorMessage = "No user found with email: " + email;

        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
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

        // TODO: Check with phoneNumber and fullName also.

        if (userRepository.findByEmail(email).isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }

    private Role getRoleInfo(UUID roleId) {
        return roleService.getEntity(roleId);
    }

    private final String generatePassword() {
        generatedPassword = new Faker().regexify("[a-z1-9A-Z]{8}");

        return passwordEncoder.encode(generatedPassword);
    }

    private void trackRequest(String action, User user, String requestDto) {
        globalUtil.trackRequest(action, "User", user.getId(), user.toString(), requestDto);
    }
}