package com.kalambo.libraryapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.TaskMapper;
import com.kalambo.libraryapi.repositories.TaskRepository;
import com.kalambo.libraryapi.responses.ITask;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public ITask create(TaskDto taskDto) {
        return taskMapper.map(taskRepository.save(taskDto.toEntity()));
    }

    @Override
    public List<ITask> getAll() {
        // TODO: Improve the mapping logic.
        List<ITask> response = new ArrayList<ITask>();
        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            response.add(taskMapper.map(task));
        }

        return response;
    }

    @Override
    public ITask getById(Integer taskId) {
        String errorMessage = "No task found with ID: " + taskId;

        Task taskInfo = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return taskMapper.map(taskInfo);
    }

    @Override
    public ITask getByTitle(String title) {
        String errorMessage = "No task found with title: " + title;

        Task taskInfo = taskRepository.findByTitle(title).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        return taskMapper.map(taskInfo);
    }

    @Override
    public ITask update(TaskDto task) {
        return taskMapper.map(taskRepository.save(updateTaskPayload(task)));
    }

    @Override
    public void delete(Integer taskId) {
        // Ensure task is present or throw 404
        ITask task = getById(taskId);

        // TODO: Delete all relational data here (if any)
        taskRepository.deleteById(task.getId());
    }

    Task updateTaskPayload(TaskDto payload) {
        // Ensure task is present or throw 404
        getById(payload.getId());

        // Get existing task info
        Task taskInfo = taskRepository.findById(payload.getId()).get();

        // Append all updatable fields here.
        if (payload.getTitle() != null)
            taskInfo.setTitle(payload.getTitle());

        if (payload.getMaxDuration() != null)
            taskInfo.setMaxDuration(payload.getMaxDuration());

        return taskInfo;
    }
}
