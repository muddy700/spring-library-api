package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.responses.IBookReview;

@Component
public class BookReviewMapper {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    public IBookReview map(BookReview bookReview) {
        IBookReview response = new IBookReview().setId(bookReview.getId())
                .setRatings(bookReview.getRatings()).setComment(bookReview.getComment())
                .setCreatedAt(bookReview.getCreatedAt()).setUpdatedAt(bookReview.getUpdatedAt())
                .setUser(userMapper.map(bookReview.getUser())).setBook(bookMapper.map(bookReview.getBook()));

        return response;
    }
}
