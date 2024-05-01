package com.kalambo.libraryapi.responses;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IRoleV3 {
    private UUID id;
    private String name;
}
