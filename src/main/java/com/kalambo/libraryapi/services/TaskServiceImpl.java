package com.kalambo.libraryapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kalambo.libraryapi.dtos.MailDto;
import com.kalambo.libraryapi.dtos.SmsDto;
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

    @Autowired
    MailService mailService;

    @Autowired
    SmsService smsService;

    @Override
    public ITask create(TaskDto taskDto) {
        Task task = taskRepository.save(taskDto.toEntity());

        // notifyAuthorByEmail(task);
        return taskMapper.map(task);
    }

    @Override
    public IPage<ITask> getAll(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        // globalUtil.formatResponse(taskPage);
        return formatResponse(taskPage);
    }

    @Override
    public ITask getById(Integer taskId) {
        String errorMessage = "No task found with ID: " + taskId;

        Task taskInfo = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage));

        // notifyAuthorBySms(taskInfo);
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

    private void notifyAuthorByEmail(Task task) {
        String message = "Hellow " + task.getAuthorName() + ", \n \n";
        message += "Your task with title: '" + task.getTitle() + "', created successfully.";

        MailDto mailPayload = new MailDto()
                .setSubject("Task Creation Notification")
                .setRecipient(task.getAuthorEmail()).setBody(message);

        mailService.sendNewMail(mailPayload);
    }

    private void notifyAuthorBySms(Task task) {
        String[] numbers = { "255717963697", "255788387525", "255718793810" };
        String message = "Hellow " + task.getAuthorName() + ", we're testing.";

        SmsDto smsDto = new SmsDto().setMessage(message)
                .setSource_addr("INFO").setEncoding(0);

        for (int i = 0; i < numbers.length; i++) {
            smsDto.addRecipient((i + 1), numbers[i]);
        }

        smsService.send(smsDto);
    }
}
