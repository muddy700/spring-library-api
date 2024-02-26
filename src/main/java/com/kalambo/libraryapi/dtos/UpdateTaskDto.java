package com.kalambo.libraryapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {
    private Integer id;
    private String title;
    private Integer maxDuration;
}
