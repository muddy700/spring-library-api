package com.kalambo.libraryapi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.kalambo.libraryapi.entities.Otp;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.OtpTypeEnum;
import com.kalambo.libraryapi.exceptions.OtpException;
import com.kalambo.libraryapi.repositories.OtpRepository;

@Service
public class OtpServiceImpl implements OtpService {
    // Otp is active only for 5 minutes
    private final long expirationTime = 5 * 60 * 1000;

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Otp create(User user, OtpTypeEnum type) {
        return otpRepository.save(getOtpPayload(user, type));
    }

    @Override
    public Otp isActive(Integer code) {
        // Check if an otp with this code exist in the db and has not expired
        Otp result = getByCode(code);
        return hasExpired(result) ? null : result;
    }

    @Override
    public Otp reCreate(Integer code) {
        Otp existingOtp = getByCode(code);

        delete(existingOtp);
        return otpRepository.save(getOtpPayload(existingOtp.getUser(), existingOtp.getType()));
    }

    @Override
    public void delete(Otp payload) {
        otpRepository.delete(payload);
    }

    private Otp getByCode(Integer code) throws OtpException {
        return otpRepository.findByCode(code).orElseThrow(() -> new OtpException("Invalid code"));
    }

    private Boolean isPresent(User user, OtpTypeEnum type) {
        return otpRepository.existsByUserAndType(user, type);
    }

    private Boolean hasExpired(Otp payload) throws OtpException {
        if (payload.getExpiresAt().before(new Date()))
            throw new OtpException("Otp has expired");

        return false;
    }

    private final Otp getOtpPayload(User user, OtpTypeEnum type) throws OtpException {
        if (isPresent(user, type))
            throw new OtpException("Another otp for this user and type already exist");

        final Integer code = (int) new Faker().number().randomNumber(6, true);
        final Date expiryDate = new Date(System.currentTimeMillis() + expirationTime);

        return new Otp(code, expiryDate, type, user);
    }
}
