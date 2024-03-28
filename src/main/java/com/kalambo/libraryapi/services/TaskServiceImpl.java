package com.kalambo.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.events.TaskCreatedEvent;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.PageMapper;
import com.kalambo.libraryapi.mappers.TaskMapper;
import com.kalambo.libraryapi.repositories.TaskRepository;
import com.kalambo.libraryapi.responses.IPage;
import com.kalambo.libraryapi.responses.ITask;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PageMapper<Task, ITask> pageMapper;

    @Override
    public ITask create(TaskDto taskDto) {
        Task task = taskRepository.save(taskDto.toEntity());

        publisher.publishEvent(new TaskCreatedEvent(task));
        return taskMapper.map(task);
    }

    @Override
    public IPage<ITask> getAll(Pageable pageable) {
        return pageMapper.paginate(taskRepository.findAll(pageable));
    }

    @Override
    public ITask getById(Integer taskId) {
        return taskMapper.map(getEntity(taskId));
    }

    @Override
    public Task getEntity(Integer taskId) {
        String errorMessage = "No task found with ID: " + taskId;
        return taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
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
        return taskMapper.map(taskRepository.save(copyNonNullValues(task)));
    }

    @Override
    public void delete(Integer taskId) {
        taskRepository.delete(getEntity(taskId));
    }

    private Task copyNonNullValues(TaskDto payload) {
        // Get existing task info
        Task taskInfo = getEntity(payload.getId());

        // Append all updatable fields here.
        if (payload.getTitle() != null)
            taskInfo.setTitle(payload.getTitle());

        if (payload.getMaxDuration() != null)
            taskInfo.setMaxDuration(payload.getMaxDuration());

        return taskInfo;
    }
}
