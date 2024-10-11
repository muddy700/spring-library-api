package com.kalambo.libraryapi.mappers;

import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.responses.ITask;
import com.kalambo.libraryapi.responses.ITaskV2;

@Component
public class TaskMapper {
    public ITask map(Task task) {
        ITask response = new ITask().setId(task.getId())
                .setUpdatedAt(task.getUpdatedAt()).setPublished(task.isPublished())
                .setAuthorEmail(task.getAuthorEmail()).setMaxDuration(task.getMaxDuration())
                .setTitle(task.getTitle()).setCreatedAt(task.getCreatedAt()).setAuthorName(task.getAuthorName());

        return response;
    }

    public ITaskV2 mapToV2(Task task) {
        return new ITaskV2().setId(task.getId())
                .setTitle(task.getTitle()).setCreatedAt(task.getCreatedAt());
    }
}
