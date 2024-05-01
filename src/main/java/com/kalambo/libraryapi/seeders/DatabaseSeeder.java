package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    private TaskSeeder taskSeeder;

    @Autowired
    private PermissionSeeder permissionSeeder;

    @Autowired
    private RoleSeeder roleSeeder;

    @Autowired
    private UserSeeder userSeeder;

    public void runSeeders() {
        taskSeeder.seed();

        permissionSeeder.seed();

        roleSeeder.seed();

        userSeeder.seed();
    }
}
