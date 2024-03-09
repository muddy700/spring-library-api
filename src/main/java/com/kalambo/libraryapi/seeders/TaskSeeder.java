package com.kalambo.libraryapi.seeders;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.repositories.TaskRepository;
import com.kalambo.libraryapi.services.TaskService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TaskSeeder {
    /**
     *  ! Use repositories only for Read operations and
     *  Services for other operations(Create, Update and Delete)
     */

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    public void seed() {
        seed(10);
    }

    public void seed(Integer targetedTaskCount) {
        Integer totalTasks = (int) taskRepository.count();

        if (totalTasks < targetedTaskCount) {
            log.info("Starting Task seeding...");
            Faker faker = new Faker();

            for (int i = 0; i < (targetedTaskCount - totalTasks); i++) {
                saveTask(formatTaskPayload(i, faker));
            }

            log.info("Task seeding completed successfully.");
        } else
            log.info("Task seeding skipped, you have " + totalTasks + " Tasks.");
    }

    private TaskDto formatTaskPayload(int round, Faker faker) {
        String title = faker.bothify("Task with name ???? and number ####");

        TaskDto payload = new TaskDto().setTitle(title)
                .setMaxDuration(2 * round + 1).setAuthorName(faker.name().fullName())
                .setAuthorEmail(faker.bothify("??????###@gmail.com"));

        return payload;
    }

    private void saveTask(TaskDto payload) {
        Optional<Task> optionalTask = taskRepository.findByTitle(payload.getTitle());

        if (optionalTask.isPresent())
            log.info("Task with title : '" + payload.getTitle() + "', already exist.");
        else
            taskService.create(payload);
    }
}
