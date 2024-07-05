package com.kalambo.libraryapi.services;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.ITask;
import com.kalambo.libraryapi.responses.ITaskV2;

public interface TaskService {
    Integer create(TaskDto taskDto);

    IPage<ITaskV2> getAll(Pageable pageable);

    ITask getById(Integer taskId);

    Task getEntity(Integer taskId);

    ITask getByTitle(String title);

    void update(TaskDto task);

    void delete(Integer taskId);
}
