package com.kalambo.libraryapi.dtos;

import com.kalambo.libraryapi.entities.Book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class BookDto {
    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Cover-Image is required")
    private String coverImage;

    @NotBlank(message = "Author-Name is required")
    private String authorName;

    public Book toEntity() {
        return new Book().setTitle(title)
                .setDescription(description).setContent(content)
                .setCoverImage(coverImage).setAuthorName(authorName);
    }
}
