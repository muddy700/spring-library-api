package com.kalambo.libraryapi.responses;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IUserV2 {
    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;

    private String gender;
    private Boolean enabled;
    private String roleName;
}
