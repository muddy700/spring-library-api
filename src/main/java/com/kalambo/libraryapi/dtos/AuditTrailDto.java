package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.entities.AuditTrail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuditTrailDto {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Action is required")
    @Size(max = 20)
    private String action;

    @NotBlank(message = "Resource-Name is required")
    @Size(max = 25)
    private String resourceName;

    @NotBlank(message = "Description is required")
    private String description;

    @Size(max = 50)
    private String resourceId;

    private String previousValues;
    private String updatedValues;

    public AuditTrail toEntity() {
        return new AuditTrail().setAction(action).setResourceName(resourceName).setDescription(description)
                .setResourceId(resourceId).setPreviousValues(previousValues).setUpdatedValues(updatedValues);
    }
}
