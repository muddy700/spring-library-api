package com.kalambo.libraryapi.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class IBookReviewV3 {
    private UUID id;
    private Integer ratings;
    private String comment;
}
