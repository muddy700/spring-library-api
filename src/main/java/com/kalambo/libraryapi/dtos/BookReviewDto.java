package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.entities.BookReview;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewDto {
    @NotBlank(message = "Ratings is required")
    @Min(1)
    @Max(5)
    private Integer ratings;

    private String comment;

    @NotBlank(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Book ID is required")
    private UUID bookId;

    public BookReview toEntity() {
        return new BookReview().setRatings(ratings).setComment(comment);
    }
}
