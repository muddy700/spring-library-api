package com.kalambo.libraryapi.services;

import com.kalambo.libraryapi.entities.Otp;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.OtpTypeEnum;

public interface OtpService {
    Otp create(User user, OtpTypeEnum type);

    Otp isActive(Integer code);

    Otp reCreate(Integer code);

    void delete(Otp payload);
}
