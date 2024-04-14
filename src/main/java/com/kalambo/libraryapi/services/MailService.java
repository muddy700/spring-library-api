package com.kalambo.libraryapi.services;

import com.kalambo.libraryapi.dtos.MailDto;

public interface MailService {
    void send(MailDto mailDto);
}
