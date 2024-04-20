package com.kalambo.libraryapi.dtos;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class MailDto {
    private String subject;
    private String body;
    private List<String> recipients = new ArrayList<String>();

    public MailDto(String email, String mailSubject, String message) {
        addRecipient(email);
        body = message;
        subject = mailSubject;
    }

    public MailDto addRecipient(String email) {
        recipients.add(email);
        return this;
    }

    public MailDto addRecipients(List<String> emailsList) {
        recipients.addAll(emailsList);
        return this;
    }
}
