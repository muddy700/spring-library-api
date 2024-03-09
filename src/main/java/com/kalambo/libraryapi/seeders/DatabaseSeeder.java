package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    TaskSeeder taskSeeder;

    @EventListener
    public void runSeeders(ContextRefreshedEvent event) {
        taskSeeder.seed();
    }
}
