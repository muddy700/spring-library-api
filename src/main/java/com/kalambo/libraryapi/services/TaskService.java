package com.kalambo.libraryapi.services;

import java.util.List;

import com.kalambo.libraryapi.dtos.UpdateTaskDto;
import com.kalambo.libraryapi.entities.Task;

public interface TaskService {
    Task create(Task task);

    List<Task> getAll();

    Task getById(Integer taskId);

    Task update(UpdateTaskDto task);

    void delete(Integer taskId);
}
