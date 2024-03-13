package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.Task;

public class TaskCreatedEvent extends BaseEvent<Task> {

    public TaskCreatedEvent(Task payload) {
        super(payload);
    }
}
