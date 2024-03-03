package com.kalambo.libraryapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kalambo.libraryapi.entities.Task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    @NotNull
    @Size(min = 5, max = 20)
    private String title;
    private Integer maxDuration;

    @JsonProperty("isPublic")
    private boolean isPublic;

    public Task toEntity() {
        return new Task().setTitle(title)
                .setMaxDuration(maxDuration).setPublic(isPublic);
    }
}
