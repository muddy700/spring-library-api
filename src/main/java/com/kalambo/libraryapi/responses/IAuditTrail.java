package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IAuditTrail {
    private UUID id;
    private String action;
    private String resourceName;

    private String resourceId;
    private String description;

    private String updatedValues;
    private String previousValues;

    private Date createdAt;
}
