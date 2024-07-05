package com.kalambo.libraryapi.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.ChangePasswordDto;
import com.kalambo.libraryapi.dtos.ForgotPasswordDto;
import com.kalambo.libraryapi.dtos.LoginDto;
import com.kalambo.libraryapi.entities.AuthToken;
import com.kalambo.libraryapi.entities.Otp;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.enums.CommunicationChannelEnum;
import com.kalambo.libraryapi.enums.OtpTypeEnum;
import com.kalambo.libraryapi.enums.TokenTypeEnum;
import com.kalambo.libraryapi.events.ForgotPasswordEvent;
import com.kalambo.libraryapi.events.OtpCreatedEvent;
import com.kalambo.libraryapi.events.PasswordChangedEvent;
import com.kalambo.libraryapi.events.TokenRecreatedEvent;
import com.kalambo.libraryapi.exceptions.InvalidPasswordException;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.responses.ITokenVerification;
import com.kalambo.libraryapi.responses.IForgotPassword;
import com.kalambo.libraryapi.responses.ILogin;
import com.kalambo.libraryapi.responses.ISuccess;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private OtpService otpService;

    public ILogin authenticate(LoginDto payload) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword()));

        User user = (User) auth.getPrincipal();
        String authToken = jwtService.generateToken(user.getUsername());
        long expirationTime = jwtService.getExpirationTime();

        return new ILogin(authToken, expirationTime, userMapper.map(user));
    }

    @Override
    public User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken)
            throw new AccessDeniedException("Request not authenticated");

        return (User) auth.getPrincipal();
    }

    @Override
    public UUID getPrincipalId() {
        return getPrincipal().getId();
    }

    @Override
    public String getPrincipalUsername() {
        return getPrincipal().getUsername();
    }

    @Override
    public ITokenVerification verifyAuthToken(String verificationToken) {
        AuthToken tokenInfo = authTokenService.isActive(verificationToken);

        String message = "Token";
        User user = tokenInfo.getUser();

        if (tokenInfo.getType() == TokenTypeEnum.EMAIL_VERIFICATION) {
            message = "Email";
            user.setEmailVerifiedAt(new Date());
        }

        if (List.of(TokenTypeEnum.EMAIL_VERIFICATION, TokenTypeEnum.PASSWORD_RESET).contains(tokenInfo.getType()))
            userService.updatePassword(user, verificationToken);

        authTokenService.delete(tokenInfo);
        return verificationResult(user, message + " verified successfully.");
    }

    @Override
    public void changePassword(ChangePasswordDto payload) {
        User user = getPrincipal();

        if (!passwordEncoder.matches(payload.getOldPassword(), user.getPassword()))
            throw new InvalidPasswordException("Invalid old password");

        if (passwordEncoder.matches(payload.getNewPassword(), user.getPassword()))
            throw new InvalidPasswordException("Your old password can not be the new password");

        userService.updatePassword(user, payload.getNewPassword());
        publisher.publishEvent(new PasswordChangedEvent(user));
    }

    @Override
    public IForgotPassword forgotPassword(ForgotPasswordDto payload) {
        User user = userService.getEntity(payload.getEmail());

        // Check if user has verified his email on account creation
        checkEmailVerificationStatus(user);

        publisher.publishEvent(new ForgotPasswordEvent(user, payload.getChannel()));

        String message = null;
        CommunicationChannelEnum channelUsed = null;

        if (payload.getChannel() == CommunicationChannelEnum.SMS && user.getPhoneVerifiedAt() != null) {
            channelUsed = CommunicationChannelEnum.SMS;
            message = getSuccessMessageForOtp(user, OtpTypeEnum.PASSWORD_RESET);
        } else {
            channelUsed = CommunicationChannelEnum.EMAIL;
            message = getSuccessMessageForEmail(TokenTypeEnum.PASSWORD_RESET);
        }

        return new IForgotPassword(message, channelUsed);
    }

    @Override
    public ISuccess resendVerificationToken(String currentToken) {
        AuthToken newToken = authTokenService.reCreate(currentToken);
        publisher.publishEvent(new TokenRecreatedEvent(newToken));

        return new ISuccess(getSuccessMessageForEmail(newToken.getType()), newToken.getId().toString());
    }

    private void checkEmailVerificationStatus(User user) throws AccessDeniedException {
        final String message = "Account email is not verified, kindly check your email and click the verification link to proceed.";

        if (user.getEmailVerifiedAt() == null)
            throw new AccessDeniedException(message);
    }

    @Override
    public ITokenVerification verifyOtp(Integer verificationCode) {
        Otp otpInfo = otpService.isActive(verificationCode);

        String message = "Code";
        User user = otpInfo.getUser();

        if (otpInfo.getType() == OtpTypeEnum.PHONE_VERIFICATION) {
            message = "Phone number";
            user.setPhoneVerifiedAt(new Date());
        }

        else if (otpInfo.getType() == OtpTypeEnum.PASSWORD_RESET)
            userService.updatePassword(user, "" + verificationCode);

        otpService.delete(otpInfo);
        return verificationResult(user, message + " verified successfully.");
    }

    private final ITokenVerification verificationResult(User user, String message) {
        return new ITokenVerification(message, jwtService.generateToken(user.getUsername()))
                .setEmail(user.getEmail()).setExpiresIn(jwtService.getExpirationTime());
    }

    @Override
    public ISuccess verifyPhoneNumber() {
        User user = getPrincipal();

        if (user.getPhoneVerifiedAt() != null)
            throw new AccessDeniedException("Phone number already verified");

        Otp otp = otpService.create(user, OtpTypeEnum.PHONE_VERIFICATION);
        publisher.publishEvent(new OtpCreatedEvent(otp));

        return new ISuccess(getSuccessMessageForOtp(user, otp.getType()), otp.getId().toString());
    }

    @Override
    public ISuccess resendOtp(Integer currentCode) {
        Otp newOtp = otpService.reCreate(currentCode);
        publisher.publishEvent(new OtpCreatedEvent(newOtp));

        return new ISuccess(getSuccessMessageForOtp(newOtp.getUser(), newOtp.getType()), newOtp.getId().toString());
    }

    private final String getSuccessMessageForOtp(User user, OtpTypeEnum otpType) {
        final String initial = (otpType == OtpTypeEnum.PASSWORD_RESET) ? "Password reset" : "Verification";

        return initial + " code sent to your phone number ending with: "
                + user.getPhoneNumber().substring(user.getPhoneNumber().length() - 2);
    }

    private final String getSuccessMessageForEmail(TokenTypeEnum tokenType) {
        final String initial = (tokenType == TokenTypeEnum.PASSWORD_RESET) ? "Password reset" : "Verification";

        return initial + " link sent successfully, kindly check your email to proceed";
    }
}
