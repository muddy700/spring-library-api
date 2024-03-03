package com.kalambo.libraryapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.dtos.CreateTaskDto;
import com.kalambo.libraryapi.dtos.UpdateTaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Task", description = "Manage user tasks.")
@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task.", description = "Some description.")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto payload) {
        log.info("POST - /api/v1/tasks");
        Task createdTask = taskService.create(payload.toEntity());

        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all tasks.", description = "Some description.")
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("GET - /api/v1/tasks");
        List<Task> allTaks = taskService.getAll();

        return ResponseEntity.ok(allTaks);
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single task by id.", description = "Some description.")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Integer taskId) {
        log.info("GET - /api/v1/tasks/" + taskId);
        Task task = taskService.getById(taskId);

        return ResponseEntity.ok(task);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update task details.", description = "Some description.")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Integer taskId, @RequestBody UpdateTaskDto payload) {
        log.info("PUT - /api/v1/tasks/" + taskId);

        payload.setId(taskId);
        Task updatedTask = taskService.update(payload);

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
