package com.kalambo.libraryapi.services;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.kalambo.libraryapi.dtos.SmsDto;
import com.kalambo.libraryapi.exceptions.ExternalAPIException;
import com.kalambo.libraryapi.responses.ISms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    // Variables declaration
    private final RestClient client = RestClient.create();

    @Value("${beem.sms.api-url}")
    private String smsApi;

    @Value("${beem.sms.api-key}")
    private String username;

    @Value("${beem.sms.api-secret}")
    private String password;

    @Override
    public void send(SmsDto smsDto) {
        try {
            client.post().uri(smsApi).contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", encodeAuthInfo())
                    .body(smsDto).retrieve().body(ISms.class);

            log.info("SMS sent successfully.");
        } catch (Exception ex) {
            log.error("Failed to send SMS" + " :: " + ex.getMessage());
            throw new ExternalAPIException("SMS API ==> " + ex.getMessage());
        }
    }

    private String encodeAuthInfo() {
        String auth = username + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

        return encodedAuth;
    }
}