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
public class UpdateRoleDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean active;
}
