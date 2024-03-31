package com.kalambo.libraryapi.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class IValidation {
    private String fieldName;
    private String errorMessage;
}
