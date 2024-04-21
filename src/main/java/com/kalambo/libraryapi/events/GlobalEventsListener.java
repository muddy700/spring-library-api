package com.kalambo.libraryapi.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.AuthToken;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.CommunicationChannelEnum;
import com.kalambo.libraryapi.enums.TokenTypeEnum;
import com.kalambo.libraryapi.notifications.MailNotifier;
import com.kalambo.libraryapi.notifications.SmsNotifier;
import com.kalambo.libraryapi.seeders.DatabaseSeeder;

/*
 * Centralized events listener for the whole project.
 */
@Component
public class GlobalEventsListener {
    @Autowired
    private DatabaseSeeder databaseSeeder;

    @Autowired
    private SmsNotifier smsNotifier;

    @Autowired
    private MailNotifier mailNotifier;

    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        databaseSeeder.runSeeders();
    }

    @Async
    @EventListener
    public void onTaskCreation(TaskCreatedEvent event) {
        // mailNotifier.onTaskCreation(event.getPayload());
    }

    @Async
    @EventListener
    public void onUserCreation(UserCreatedEvent event) {
        mailNotifier.onUserCreation(event.getPayload());
    }

    @Async
    @EventListener
    public void onBookCreation(BookCreatedEvent event) {
        mailNotifier.onBookCreation(event.getPayload());
    }

    @Async
    @EventListener
    public void onPasswordChange(PasswordChangedEvent event) {
        mailNotifier.onPasswordChange(event.getPayload());
    }

    @Async
    @EventListener
    public void onForgotPassword(ForgotPasswordEvent event) {
        User user = event.getPayload();

        if (event.getChannel() == CommunicationChannelEnum.SMS && user.getPhoneVerifiedAt() != null)
            smsNotifier.onForgotPassword(user);
        else
            mailNotifier.onForgotPassword(user);
    }

    @Async
    @EventListener
    public void onOtpCreation(OtpCreatedEvent event) {
        smsNotifier.onOtpCreation(event.getPayload());
    }

    @Async
    @EventListener
    public void onTokenRecreatedEvent(TokenRecreatedEvent event) {
        AuthToken token = event.getPayload();

        if (token.getType() == TokenTypeEnum.EMAIL_VERIFICATION)
            mailNotifier.onUserCreation(token.getUser(), token.getToken());

        else if (token.getType() == TokenTypeEnum.PASSWORD_RESET)
            mailNotifier.onForgotPassword(token.getUser(), token.getToken());
    }
}
