package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.Book;

public class BookCreatedEvent extends BaseEvent<Book> {

    public BookCreatedEvent(Book payload) {
        super(payload);
    }
}
