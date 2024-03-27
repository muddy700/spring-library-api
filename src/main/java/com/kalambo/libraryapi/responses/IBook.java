package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IBook {
    private UUID id;
    private String title;
    private String description;
    private String registrationNumber;

    private String content;
    private String coverImage;
    private String authorName;
    private Boolean enabled;

    private Integer ratings;
    private List<IBookReview> reviews;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
