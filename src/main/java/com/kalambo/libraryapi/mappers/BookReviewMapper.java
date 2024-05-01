package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.responses.IBookReview;
import com.kalambo.libraryapi.responses.IBookReviewV2;
import com.kalambo.libraryapi.responses.IBookReviewV3;

@Component
public class BookReviewMapper {
    @Autowired
    private UserMapper userMapper;

    public IBookReview map(BookReview bookReview) {
        IBookReview response = new IBookReview().setId(bookReview.getId())
                .setRatings(bookReview.getRatings()).setComment(bookReview.getComment())
                .setCreatedAt(bookReview.getCreatedAt()).setUpdatedAt(bookReview.getUpdatedAt())
                .setUser(userMapper.mapToV3(bookReview.getUser())).setBookId(bookReview.getBook().getId());

        return response;
    }

    public IBookReviewV2 mapToV2(BookReview bookReview) {
        IBookReviewV2 response = new IBookReviewV2().setId(bookReview.getId())
                .setRatings(bookReview.getRatings()).setComment(bookReview.getComment())
                .setUpdatedAt(bookReview.getUpdatedAt()).setUser(userMapper.mapToV3(bookReview.getUser()));

        return response;
    }

    public IBookReviewV3 mapToV3(BookReview bookReview) {
        return new IBookReviewV3().setId(bookReview.getId())
                .setRatings(bookReview.getRatings()).setComment(bookReview.getComment());
    }
}
