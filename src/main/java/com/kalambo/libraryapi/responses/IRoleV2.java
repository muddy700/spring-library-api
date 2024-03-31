package com.kalambo.libraryapi.responses;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IRoleV2 {
    private UUID id;
    private String name;

    private Boolean active;
    private Integer permissionsCount;
}
