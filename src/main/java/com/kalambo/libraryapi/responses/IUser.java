package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IUser {
    private UUID id;
    private String email;
    private String phoneNumber;

    private String fullName;
    private Date emailVerifiedAt;
    private Date phoneVerifiedAt;

    private String gender;
    private Boolean enabled;
    private Boolean firstTimeLogin;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private IRole role;
}
