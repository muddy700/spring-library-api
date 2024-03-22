package com.kalambo.libraryapi.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.UserDto;
import com.kalambo.libraryapi.dtos.UpdateUserDto;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.events.UserCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceDuplicationException;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.IUser;

@Service
public class UserServiceImpl implements UserService {
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
        User user = userRepository.save(userDto.toEntity());

        publisher.publishEvent(new UserCreatedEvent(user));
        return userMapper.map(user);
    }

    @Override
    public IPage<IUser> getAll(Pageable pageable) {
        return pageMapper.paginate(userRepository.findAll(pageable));
    }

    @Override
    public IUser getById(UUID userId) {
        String errorMessage = "No user found with ID: " + userId;

        User userInfo = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return userMapper.map(userInfo);
    }

    @Override
    public IUser update(UpdateUserDto payload) {
        return userMapper.map(userRepository.save(copyNonNullValues(payload)));
    }

    @Override
    public void delete(UUID userId) {
        // Ensure user is present or throw 404
        getById(userId);

        // TODO: Delete all relational data here (if any)
        userRepository.deleteById(userId);
    }

    private User copyNonNullValues(UpdateUserDto payload) {
        // Ensure user is present or throw 404
        getById(payload.getId());

        // Get existing user info
        User userInfo = userRepository.findById(payload.getId()).get();

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

        return userInfo;
    }

    private void checkDuplication(String email) {
        String errorMessage = "User with email: " + email + ", already exist";
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent())
            throw new ResourceDuplicationException(errorMessage);
    }
}