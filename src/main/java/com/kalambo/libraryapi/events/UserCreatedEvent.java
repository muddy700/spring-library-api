package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.User;

public class UserCreatedEvent extends BaseEvent<User> {

    public UserCreatedEvent(User payload) {
        super(payload);
    }
}
