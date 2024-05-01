package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.CommunicationChannelEnum;

import lombok.Getter;

@Getter

public class ForgotPasswordEvent extends BaseEvent<User> {
    private CommunicationChannelEnum channel;

    public ForgotPasswordEvent(User payload, CommunicationChannelEnum selectedChannel) {
        super(payload);
        channel = selectedChannel;
    }
}
