package com.kalambo.libraryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.kalambo.libraryapi.responses.ISuccess;
import com.kalambo.libraryapi.responses.ITask;
import com.kalambo.libraryapi.responses.ITaskV2;
import com.kalambo.libraryapi.services.TaskService;
import com.kalambo.libraryapi.utilities.GlobalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task", description = "Manage user tasks.")

public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private GlobalUtil globalUtil;

    @PostMapping
    @Validated(OnCreate.class)
    @Operation(summary = "Create a new task.", description = "Some description.")
    public ISuccess createTask(@Valid @RequestBody TaskDto payload) {
        logRequest("POST", "");

        return successResponse("create", taskService.create(payload));
    }

    @GetMapping
    @Operation(summary = "Retrieve all tasks.", description = "Some description.")
    public IPage<ITaskV2> getAllTasks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logRequest("GET", "");

        return taskService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a single task by id.", description = "Some description.")
    public ITask getTaskById(@PathVariable("id") @Min(1) Integer taskId) {
        logRequest("GET", "/" + taskId);

        return taskService.getById(taskId);
    }

    @PutMapping("{id}")
    @Validated(OnUpdate.class)
    @Operation(summary = "Update task details.", description = "Some description.")
    public ISuccess updateTask(@PathVariable("id") Integer taskId, @Valid @RequestBody TaskDto payload) {
        logRequest("PUT", "/" + taskId);

        taskService.update(payload.setId(taskId));
        return successResponse("update", taskId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a single task by id.", description = "Some description.")
    public ISuccess deleteTaskById(@PathVariable("id") Integer taskId) {
        logRequest("DELETE", "/" + taskId);

        taskService.delete(taskId);
        return successResponse("delete", taskId);
    }

    private final ISuccess successResponse(String action, Integer resourceId) {
        final String message = "Task " + action + "d successfully.";
        return new ISuccess(message, String.valueOf(resourceId));
    }

    private void logRequest(String httpMethod, String endpoint) {
        globalUtil.logRequest(httpMethod, "tasks" + endpoint);
    }
}
