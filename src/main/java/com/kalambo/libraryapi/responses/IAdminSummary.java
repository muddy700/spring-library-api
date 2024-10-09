package com.kalambo.libraryapi.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IAdminSummary {
    Long users;
    Long books;
    Long roles;
    Long permissions;
}