package com.kalambo.libraryapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.UpdateTaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.repositories.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Integer taskId) {
        String errorMessage = "No task found with ID: " + taskId;

        return taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));
    }

    @Override
    public Task update(UpdateTaskDto task) {
        return taskRepository.save(updateTaskPayload(task));
    }

    @Override
    public void delete(Integer taskId) {
        // Ensure task is present or throw 404
        Task task = getById(taskId);

        taskRepository.delete(task);
    }

    Task updateTaskPayload(UpdateTaskDto payload) {
        // Ensure task is present or throw 404
        Task taskInfo = getById(payload.getId());

        // Append all updatable fields here.
        if (payload.getTitle() != null)
            taskInfo.setTitle(payload.getTitle());

        if (payload.getMaxDuration() != null)
            taskInfo.setMaxDuration(payload.getMaxDuration());

        return taskInfo;
    }
}
