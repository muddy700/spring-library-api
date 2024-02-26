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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto payload) {
        Task createdTask = taskService.create(payload.toEntity());
        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTaks = taskService.getAll();
        return ResponseEntity.ok(allTaks);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Integer taskId) {
        Task task = taskService.getById(taskId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Integer taskId, @RequestBody UpdateTaskDto payload) {
        payload.setId(taskId);
        Task updatedTask = taskService.update(payload);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable("id") Integer taskId) {
        taskService.delete(taskId);
        String message = "Task with ID: " + taskId + " deleted successful.";

        return ResponseEntity.ok(message);
    }
}
