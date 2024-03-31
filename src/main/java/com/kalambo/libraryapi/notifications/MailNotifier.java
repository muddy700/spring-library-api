package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.services.MailService;

@Service
public class MailNotifier {
    @Autowired
    private MailService mailService;

    public void onTaskCreation(Task task) {
        String message = "Hellow " + task.getAuthorName() + ", \n \n";
        message += "Your task with title: '" + task.getTitle() + "', created successfully.";

        MailDto mailPayload = new MailDto()
                .setSubject("Task Creation Notification")
                .setRecipient(task.getAuthorEmail()).setBody(message);

        mailService.sendNewMail(mailPayload);
    }

    public void onUserCreation(User user) {
        // TODO: Notify user about the account creation and send them credentials here
    }

    public void onBookCreation(Book book) {
        // TODO: Notify students who subscribed about the new book added and send them link to view it
    }
}
