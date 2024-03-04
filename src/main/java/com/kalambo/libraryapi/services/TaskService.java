package com.kalambo.libraryapi.services;

import java.util.List;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.dtos.UpdateTaskDto;
import com.kalambo.libraryapi.responses.ITask;

public interface TaskService {
    ITask create(TaskDto taskDto);

    List<ITask> getAll();

    ITask getById(Integer taskId);

    ITask update(UpdateTaskDto task);

    void delete(Integer taskId);
}
