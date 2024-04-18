package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.SmsDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.services.SmsService;

@Service
public class SmsNotifier {
    @Autowired
    private SmsService smsService;

    public void onTaskCreation(Task task) {
        String[] numbers = { "255717963697" };
        String message = "Hellow " + task.getAuthorName() + ", we're testing.";

        SmsDto smsDto = new SmsDto().setMessage(message)
                .setSource_addr("Brungas Inc").setEncoding(0);

        for (int i = 0; i < numbers.length; i++) {
            smsDto.addRecipient((i + 1), numbers[i]);
        }

        smsService.send(smsDto);
    }
}
