package com.kalambo.libraryapi.services;

import com.kalambo.libraryapi.dtos.SmsDto;

public interface SmsService {
    void send(SmsDto smsDto);
}
