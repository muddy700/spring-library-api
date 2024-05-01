package com.kalambo.libraryapi.dtos;

import com.kalambo.libraryapi.entities.Permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class PermissionDto {
    @NotBlank(message = "Permission resource-name is required")
    @Size(min = 2, max = 20)
    private String resourceName;

    @NotBlank(message = "Permission action is required")
    @Size(min = 4, max = 30)
    private String action;

    @NotBlank(message = "Permission description is required")
    @Size(min = 10, max = 150)
    private String description;

    public Permission toEntity() {
        return new Permission().setAction(action)
                .setResourceName(resourceName).setDescription(description);
    }
}
