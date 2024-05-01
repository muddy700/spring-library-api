package com.kalambo.libraryapi.responses;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IAuditTrailV3 {
    private UUID id;
    private IUserV3 user;
    private String description;
}
