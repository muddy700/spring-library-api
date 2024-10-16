package com.kalambo.libraryapi.responses;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class ITask {
    private Integer id;
    private String title;

    private String authorName;
    private String authorEmail;
    private Integer maxDuration;

    private Date createdAt;
    private Date updatedAt;
    private boolean published;
}