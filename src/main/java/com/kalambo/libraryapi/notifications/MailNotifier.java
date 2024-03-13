package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.services.MailService;

@Service
public class MailNotifier {
    @Autowired
    MailService mailService;

    public void onTaskCreation(Task task) {
        String message = "Hellow " + task.getAuthorName() + ", \n \n";
        message += "Your task with title: '" + task.getTitle() + "', created successfully.";

        MailDto mailPayload = new MailDto()
                .setSubject("Task Creation Notification")
                .setRecipient(task.getAuthorEmail()).setBody(message);

        mailService.sendNewMail(mailPayload);
    }
}
