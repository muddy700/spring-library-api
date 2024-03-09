package com.kalambo.libraryapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.TaskDto;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.exceptions.ResourceNotFoundException;
import com.kalambo.libraryapi.mappers.TaskMapper;
import com.kalambo.libraryapi.repositories.TaskRepository;
import com.kalambo.libraryapi.responses.IPage;
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
    public IPage<ITask> getAll(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);

        return formatResponse(taskPage);
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

    private Task updateTaskPayload(TaskDto payload) {
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

    // TODO: Create a global utility function for this operation
    private IPage<ITask> formatResponse(Page<Task> taskPage) {
        List<ITask> contents = new ArrayList<ITask>(taskPage.getSize());

        for (Task task : taskPage.getContent()) {
            contents.add(taskMapper.map(task));
        }

        IPage<ITask> response = new IPage<ITask>().setItems(contents)
                .setTotalPages(taskPage.getTotalPages()).setCurrentPage(taskPage.getNumber())
                .setTotalItems(taskPage.getTotalElements()).setCurrentSize(taskPage.getSize());

        return response;
    }
}
