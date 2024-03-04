package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.responses.ITask;

@Component
public class TaskMapper {
    public ITask map(Task task) {
        ITask response = new ITask().setId(task.getId())
                .setTitle(task.getTitle()).setCreatedAt(task.getCreatedAt());

        return response;
    }
}
