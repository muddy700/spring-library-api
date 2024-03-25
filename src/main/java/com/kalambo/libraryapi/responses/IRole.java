package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IRole {
    private UUID id;
    private String name;
    private String description;

    private Boolean active;
    List<IPermission> permissions;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
