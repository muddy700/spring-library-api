package com.kalambo.libraryapi.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.notifications.MailNotifier;
import com.kalambo.libraryapi.notifications.SmsNotifier;
import com.kalambo.libraryapi.seeders.DatabaseSeeder;

import lombok.extern.slf4j.Slf4j;

/*
 * Centralized events listener for the whole project.
 */
@Slf4j
@Component
public class GlobalEventsListener {
    @Autowired
    private DatabaseSeeder databaseSeeder;

    @Autowired
    private SmsNotifier smsNotifier;

    @Autowired
    private MailNotifier emailNotifier;

    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        databaseSeeder.runSeeders();
    }

    @Async
    @EventListener
    public void onTaskCreation(TaskCreatedEvent event) {
        // smsNotifier.onTaskCreation(event.getPayload());
        // emailNotifier.onTaskCreation(event.getPayload());
    }

    @Async
    @EventListener
    public void onUserCreation(UserCreatedEvent event) {
        emailNotifier.onUserCreation(event.getPayload());
    }
}
