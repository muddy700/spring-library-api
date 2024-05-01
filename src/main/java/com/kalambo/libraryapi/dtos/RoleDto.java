package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kalambo.libraryapi.entities.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class RoleDto {
    @NotBlank(message = "Role name is required")
    @Size(min = 4, max = 25)
    private String name;

    @NotBlank(message = "Role description is required")
    @Size(min = 10, max = 200)
    private String description;

    private List<UUID> permissionsIds = new ArrayList<UUID>();

    public Role toEntity() {
        return new Role()
                .setName(name).setDescription(description);
    }
}
