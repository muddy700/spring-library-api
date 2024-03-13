package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    TaskSeeder taskSeeder;

    public void runSeeders() {
        taskSeeder.seed();
    }
}
