package com.kalambo.libraryapi.responses;

import com.kalambo.libraryapi.enums.CommunicationChannelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class IForgotPassword {
    private String message;
    private CommunicationChannelEnum channelUsed;
}
