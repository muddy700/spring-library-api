package com.kalambo.libraryapi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.dtos.groups.OnCreate;
import com.kalambo.libraryapi.dtos.groups.OnUpdate;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.ITask;
import com.kalambo.libraryapi.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Task", description = "Manage user tasks.")
@Slf4j
@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    @Validated(OnCreate.class)
    @Operation(summary = "Create a new task.", description = "Some description.")
    public ResponseEntity<ITask> createTask(@Valid @RequestBody TaskDto payload) {
        log.info("POST - /api/v1/tasks");
        ITask createdTask = taskService.create(payload);

        return new ResponseEntity<ITask>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all tasks.", description = "Some description.")
    public ResponseEntity<IPage<ITask>> getAllTasks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET - /api/v1/tasks");

        return ResponseEntity.ok(taskService.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single task by id.", description = "Some description.")
    public ResponseEntity<ITask> getTaskById(@PathVariable("id") @Min(1) Integer taskId) {
        log.info("GET - /api/v1/tasks/" + taskId);
        ITask task = taskService.getById(taskId);

        return ResponseEntity.ok(task);
    }

    @PutMapping("{id}")
    @Validated(OnUpdate.class)
    @Operation(summary = "Update task details.", description = "Some description.")
    public ResponseEntity<ITask> updateTask(@PathVariable("id") Integer taskId, @Valid @RequestBody TaskDto payload) {
        log.info("PUT - /api/v1/tasks/" + taskId);

        payload.setId(taskId);
        ITask updatedTask = taskService.update(payload);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single task by id.", description = "Some description.")
    public ResponseEntity<String> deleteTaskById(@PathVariable("id") Integer taskId) {
        log.warn("DELETE - /api/v1/tasks/" + taskId);

        taskService.delete(taskId);
        String message = "Task with ID: " + taskId + " deleted successful.";

        return ResponseEntity.ok(message);
    }
}
