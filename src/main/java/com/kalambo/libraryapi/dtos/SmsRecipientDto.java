package com.kalambo.libraryapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SmsRecipientDto {
    private Integer recipient_id;
    private String dest_addr;
}
