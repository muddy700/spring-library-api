package com.kalambo.libraryapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDto {
    private String recipient;
    private String subject;
    private String body;
}
