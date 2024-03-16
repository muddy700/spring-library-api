package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    private TaskSeeder taskSeeder;

    @Autowired
    private PermissionSeeder permissionSeeder;

    public void runSeeders() {
        taskSeeder.seed();
        permissionSeeder.seed();
    }
}
