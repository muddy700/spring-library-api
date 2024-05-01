package com.kalambo.libraryapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;
import com.kalambo.libraryapi.exceptions.ExternalAPIException;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(MailDto mailDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(message);
            final String emailSignature = "<br> <br> Kind Regards,<br> The Brungas Team.";

            msgHelper.setSubject(mailDto.getSubject());
            msgHelper.setTo(mailDto.getRecipients().toArray(new String[0]));
            msgHelper.setText(mailDto.getBody() + emailSignature, true);
            msgHelper.setFrom("info.brungas@gmail.com", "Brungas Inc");

            mailSender.send(message);
            logSuccessMessage(mailDto);
        } catch (Exception ex) {
            log.error("Failed to send Mail :: " + ex.getMessage());
            throw new ExternalAPIException("Mail API ==> " + ex.getMessage());
        }
    }

    private void logSuccessMessage(MailDto payload) {
        List<String> recipients = payload.getRecipients();
        String logMessage = "Mail with subject: " + payload.getSubject() + ", sent successfully to: ";

        if (recipients.size() == 1)
            log.info(logMessage + recipients.getFirst());
        else
            log.info(logMessage + recipients.size() + " recipients");
    }
}
