package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.services.TaskService;

@Component
public class TaskSeeder {
    @Autowired
    TaskService taskService;

    public void seed() {
        Task payload = new Task().setTitle("Seed title 3")
                .setMaxDuration(15);

        taskService.create(payload);
    }
}
