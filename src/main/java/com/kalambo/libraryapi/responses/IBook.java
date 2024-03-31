package com.kalambo.libraryapi.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

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
    private List<IBookReviewV2> reviews = new ArrayList<IBookReviewV2>();

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
