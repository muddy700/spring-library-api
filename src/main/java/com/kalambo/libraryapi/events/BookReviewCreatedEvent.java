package com.kalambo.libraryapi.events;

import com.kalambo.libraryapi.entities.BookReview;

public class BookReviewCreatedEvent extends BaseEvent<BookReview> {

    public BookReviewCreatedEvent(BookReview payload) {
        super(payload);
    }
}
