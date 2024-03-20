package com.kalambo.libraryapi.schedulers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserScheduler {
    @Scheduled(cron = "@daily")
    @Async
    public void remindPasswordChange() {
        final String schedulerName = "remindPasswordChange()";

        try {
            log.info("Starting scheduler for: " + schedulerName);

            // TODO: Retrieve all users whose passwords are going to expire within the next
            // 7 days and notify them via email, and / or include password-change link.

            log.info("Scheduler: " + schedulerName + ", executed successfully.");
        } catch (Exception ex) {
            log.error("Failed to run scheduler: " + schedulerName + " ==> " + ex.getMessage(), ex);
        }
    }
}
