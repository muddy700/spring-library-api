package com.kalambo.libraryapi.responses;

import java.util.Date;
import java.util.UUID;

import com.kalambo.libraryapi.enums.GenderEnum;

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

    private GenderEnum gender;
    private Boolean enabled;
    private IRole role;
    private Date passwordChangedAt;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
