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
        notifyUser(getPasswordResetCode(user), user, OtpTypeEnum.PASSWORD_RESET);
    }

    public void onOtpCreation(Otp otp) {
        notifyUser(otp.getCode(), otp.getUser(), otp.getType());
    }

    private void notifyUser(Integer code, User user, OtpTypeEnum otpType) {
        String purpose = null;

        if (otpType == OtpTypeEnum.PASSWORD_RESET)
            purpose = "password reset";

        else if (otpType == OtpTypeEnum.PHONE_VERIFICATION)
            purpose = "phone number verification";

        final String message = code + " is your " + purpose + " code for Library MVP App.";

        smsService.send(new SmsDto(user.getPhoneNumber(), message));
    }

    private final Integer getPasswordResetCode(User user) {
        return otpService.create(user, OtpTypeEnum.PASSWORD_RESET).getCode();
    }
}
