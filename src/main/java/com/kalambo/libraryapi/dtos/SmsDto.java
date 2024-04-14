package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class SmsDto {
    private String source_addr;
    private Integer encoding;
    private String message;
    private List<SmsRecipientDto> recipients;

    public void addRecipient(Integer recipientId, String destinationAddress) {
        if (Objects.isNull(recipients)) {
            recipients = new ArrayList<SmsRecipientDto>();
        }

        recipients.add(new SmsRecipientDto(recipientId, destinationAddress));
    }
}
