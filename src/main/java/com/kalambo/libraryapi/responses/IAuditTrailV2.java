package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class IAuditTrailV2 {
    private UUID id;
    private String action;
    private String resourceName;

    private IUserV3 user;
    private Date createdAt;
}
