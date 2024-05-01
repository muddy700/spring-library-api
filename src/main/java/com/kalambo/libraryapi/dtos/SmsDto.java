package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class SmsDto {
    private String source_addr = "Brungas Inc";
    private Integer encoding = 0;
    private String message;
    private List<SmsRecipientDto> recipients = new ArrayList<SmsRecipientDto>();

    public SmsDto(String recipient, String msg) {
        message = msg;
        addRecipient(recipient);
    }

    public void addRecipient(String destinationAddress) {
        recipients.add(new SmsRecipientDto(recipients.size() + 1, destinationAddress));
    }
}
