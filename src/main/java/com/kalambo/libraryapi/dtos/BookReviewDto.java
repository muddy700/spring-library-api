package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.entities.BookReview;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class BookReviewDto {
    @NotNull(message = "Ratings is required")
    @Min(1)
    @Max(5)
    private Integer ratings;

    private UUID bookId;
    private String comment;

    public BookReview toEntity() {
        return new BookReview().setRatings(ratings).setComment(comment);
    }
}
