package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.User;

public class PasswordChangedEvent extends BaseEvent<User> {

    public PasswordChangedEvent(User payload) {
        super(payload);
    }
}
