package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.Otp;

public class OtpCreatedEvent extends BaseEvent<Otp> {

    public OtpCreatedEvent(Otp payload) {
        super(payload);
    }
}
