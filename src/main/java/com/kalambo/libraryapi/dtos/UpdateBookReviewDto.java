package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookReviewDto {
    @NotBlank(message = "Book-Review ID is required")
    private UUID id;

    @NotBlank(message = "Comment is required")
    private String comment;
}
