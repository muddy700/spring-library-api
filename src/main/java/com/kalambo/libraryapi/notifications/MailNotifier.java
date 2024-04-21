package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.TokenTypeEnum;
import com.kalambo.libraryapi.repositories.UserRepository;
import com.kalambo.libraryapi.services.AuthTokenService;
import com.kalambo.libraryapi.services.MailService;

@Service
public class MailNotifier {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenService authTokenService;

    @Value("${app.web.base-url}")
    private String webAppBaseUrl;

    public void onTaskCreation(Task task) {
        String message = "Hello " + task.getAuthorName() + "!,\n \n";
        message += "Your task with title: " + task.getTitle() + ", created successfully.";

        MailDto mailPayload = new MailDto().setSubject("Task Creation Notification").setBody(message);

        mailService.send(mailPayload.addRecipient(task.getAuthorEmail()));
    }

    public void onUserCreation(User user) {
        onUserCreation(user, getVerificationToken(user));
    }

    public void onUserCreation(User user, String token) {
        final String verificationUrl = webAppBaseUrl + "/api/v1/auth/verify-email?token=" + token;

        String message = "Hello " + user.getFullName() + "!, welcome to Library MVP App.<br> <br>";
        message += "Click the link below to verify your email and create your password.<br> <br>";
        message += "<b>NB:</b> Your username is: " + user.getEmail();
        message += "<h3><a href=" + verificationUrl + " target=\"_blank\">VERIFY</a></h3>";

        mailService.send(new MailDto(user.getEmail(), "Account Creation Notification", message));
    }

    public void onForgotPassword(User user) {
        onForgotPassword(user, getPasswordResetToken(user));
    }

    public void onForgotPassword(User user, String token) {
        final String passwordResetUrl = webAppBaseUrl + "/auth/reset-password?token=" + token;

        String message = "Hello " + user.getFullName()
                + ", click the link below to reset your password in Library MVP App.";
        message += "<h3><a href=" + passwordResetUrl + " target=\"_blank\">RESET</a></h3>";

        mailService.send(new MailDto(user.getEmail(), "Reset Password Link", message));
    }

    public void onBookCreation(Book book) {
        // TODO: Retrieve all users(students) who subscribed for email(emailSubscription
        // = true), and return only fullName and email.

        for (User user : userRepository.findAll()) {
            String message, subject = user.getFullName() + ", we just added a Book you might like";

            message = "Title: " + book.getTitle() + "\n";
            message += "Author: " + book.getAuthorName() + "\n";
            message += "Registration Number: " + book.getRegistrationNumber() + "\n \n";
            message += "Link: https://localhost:4200/books/b116f8d1-582f-4059-9cb0-5e7a44ba24da";

            mailService.send(new MailDto(user.getEmail(), subject, message));
        }
    }

    public void onPasswordChange(User user) {
        String message = "Hello " + user.getFullName() + ",<br> <br>";
        message += "This is to inform you that, the password for your account in Library MVP App has been changed.<br>";
        message += "If you did not authorize this action, then please contact your admin ASAP.";

        mailService.send(new MailDto(user.getEmail(), "Account Password Changed", message));
    }

    private final String getVerificationToken(User user) {
        return authTokenService.create(user, TokenTypeEnum.EMAIL_VERIFICATION).getToken();
    }

    private final String getPasswordResetToken(User user) {
        return authTokenService.create(user, TokenTypeEnum.PASSWORD_RESET).getToken();
    }
}

// message.replace("$_VAR_LINK", verificationUrl)
