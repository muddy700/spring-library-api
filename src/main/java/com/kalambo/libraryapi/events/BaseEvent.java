package com.kalambo.libraryapi.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseEvent<T> {
    private T payload;
}
