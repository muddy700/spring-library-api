package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.SmsDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.services.SmsService;

@Service
public class SmsNotifier {
    @Autowired
    SmsService smsService;

    public void onTaskCreation(Task task) {
        String[] numbers = { "255717963697", "255788387525", "255718793810" };
        String message = "Hellow " + task.getAuthorName() + ", we're testing.";

        SmsDto smsDto = new SmsDto().setMessage(message)
                .setSource_addr("INFO").setEncoding(0);

        for (int i = 0; i < numbers.length; i++) {
            smsDto.addRecipient((i + 1), numbers[i]);
        }

        smsService.send(smsDto);
    }
}
