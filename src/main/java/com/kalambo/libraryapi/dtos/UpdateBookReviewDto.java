package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UpdateBookReviewDto {
    private UUID reviewId;

    @NotBlank(message = "Comment is required")
    private String comment;
}
