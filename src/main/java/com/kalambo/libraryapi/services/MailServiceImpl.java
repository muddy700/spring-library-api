package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendNewMail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(mailDto.getRecipient());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getBody());

        mailSender.send(message);
    }
}
