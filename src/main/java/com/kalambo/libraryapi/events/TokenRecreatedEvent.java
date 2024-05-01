package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.AuthToken;

public class TokenRecreatedEvent extends BaseEvent<AuthToken> {

    public TokenRecreatedEvent(AuthToken payload) {
        super(payload);
    }
}
