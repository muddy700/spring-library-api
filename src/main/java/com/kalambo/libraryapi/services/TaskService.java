package com.kalambo.libraryapi.services;

import org.springframework.data.domain.Pageable;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.ITask;

public interface TaskService {
    ITask create(TaskDto taskDto);

    IPage<ITask> getAll(Pageable pageable);

    ITask getById(Integer taskId);

    ITask getByTitle(String title);

    ITask update(TaskDto task);

    void delete(Integer taskId);
}
