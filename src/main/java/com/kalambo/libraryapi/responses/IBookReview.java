package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IBookReview {
    private UUID id;
    private Integer ratings;
    private String comment;

    private IUser user;
    private UUID bookId;

    private Date createdAt;
    private Date updatedAt;
}
