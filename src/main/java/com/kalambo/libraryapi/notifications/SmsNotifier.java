package com.kalambo.libraryapi.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.SmsDto;
import com.kalambo.libraryapi.entities.Otp;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.OtpTypeEnum;
import com.kalambo.libraryapi.services.OtpService;
import com.kalambo.libraryapi.services.SmsService;

@Service
public class SmsNotifier {
    @Autowired
    private SmsService smsService;

    @Autowired
    private OtpService otpService;

    public void onForgotPassword(User user) {
        onForgotPassword(user, getPasswordResetCode(user));
    }

    public void onForgotPassword(User user, Integer code) {
        final String message = code + " is your password reset code for Library MVP App.";

        smsService.send(new SmsDto(user.getPhoneNumber(), message));
    }

    public void onOtpCreation(Otp otp) {
        String message = null;

        if (otp.getType() == OtpTypeEnum.PHONE_VERIFICATION)
            message = otp.getCode() + " is your phone number verification code for Library MVP App.";

        else if (otp.getType() == OtpTypeEnum.PASSWORD_RESET)
            message = otp.getCode() + " is your password reset code for Library MVP App.";

        smsService.send(new SmsDto(otp.getUser().getPhoneNumber(), message));
    }

    private final Integer getPasswordResetCode(User user) {
        return otpService.create(user, OtpTypeEnum.PASSWORD_RESET).getCode();
    }
}
