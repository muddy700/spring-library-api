package com.kalambo.libraryapi.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class IError {
    private Date timestamp;
    private String path;
    private String title;
    private String description;
    private List<IValidation> validations = new ArrayList<IValidation>();

    public void addValidationError(String fieldName, String errorMessage) {
        validations.add(new IValidation(fieldName, errorMessage));
    }
}
