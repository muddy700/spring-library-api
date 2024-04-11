package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.entities.AuditTrail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class AuditTrailDto {
    @NotBlank(message = "Action is required")
    @Size(max = 20)
    private String action;

    @NotBlank(message = "Resource-Name is required")
    @Size(max = 25)
    private String resourceName;

    private UUID resourceId;
    private String previousValues;
    private String updatedValues;

    public AuditTrail toEntity() {
        return new AuditTrail().setAction(action).setResourceName(resourceName)
                .setResourceId(resourceId).setPreviousValues(previousValues).setUpdatedValues(updatedValues);
    }
}
