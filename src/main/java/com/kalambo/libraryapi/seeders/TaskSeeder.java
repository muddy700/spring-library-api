package com.kalambo.libraryapi.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.services.TaskService;

@Component
public class TaskSeeder {
    @Autowired
    TaskService taskService;

    public void seed() {
        TaskDto payload = new TaskDto().setTitle("Seed title 3")
                .setMaxDuration(15);

        taskService.create(payload);
    }
}
