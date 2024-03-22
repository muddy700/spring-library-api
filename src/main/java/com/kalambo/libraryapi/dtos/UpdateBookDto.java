package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDto {
    private UUID id;
    private String title;
    private String description;

    private String content;
    private String coverImage;
    private String authorName;
    private Boolean enabled;
}
