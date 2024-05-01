package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class IBookV2 {
    private UUID id;
    private String title;
    private String registrationNumber;

    private String coverImage;
    private String authorName;
    private Integer reviewsCount;

    private Integer ratings;
    private Boolean enabled;
    private Date updatedAt;
}
