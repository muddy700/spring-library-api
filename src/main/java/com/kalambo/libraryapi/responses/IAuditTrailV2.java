package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IAuditTrailV2 {
    private UUID id;
    private String action;
    private String resourceName;

    private String actorName;
    private String actorEmail;

    private IUserV3 user;
    private Date createdAt;
}
