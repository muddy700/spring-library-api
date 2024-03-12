package com.kalambo.libraryapi.schedulers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ITaskScheduler {
    /**
     * * Below are the different ways to set the interval
     */

    // * Using milliseconds
    // @Scheduled(fixedDelay = 5000)
    // @Scheduled(fixedRate = 5000)

    // * Using milliseconds with initial delay
    // @Scheduled(initialDelay = 10000, fixedDelay = 5000)

    // * Using ISO duration format
    // @Scheduled(fixedDelayString = "PT05S")
    // @Scheduled(fixedRateString = "PT02S")

    // * Using ISO duration format with application.properties file
    // @Scheduled(fixedDelayString = "${scheduler.fixed.delay}")
    // @Scheduled(fixedRateString = "${scheduler.fixed.rate}")

    // * Using cron interval
    // @Scheduled(cron = "* * * * * *")
    // @Scheduled(cron = "@hourly")
    // @Scheduled(cron = "${scheduler.cron.interval}")

    @Async
    public void printMessage() {
        // log.info("Someone must have scheduled me just now.");
    }

    /*
     * * Example usage for scheduled jobs are:
     * 1. Wishing all system users a happy new year every 1 day of a year
     * 2. Remind users to change their passwords after every 3 months
     * 3. Remind your customers to pay bill in every end of a month
     */
}
