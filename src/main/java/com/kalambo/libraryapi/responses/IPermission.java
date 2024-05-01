package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IPermission {
    private UUID id;
    private String resourceName;
    private String action;
    private String description;
    private Date createdAt;
}