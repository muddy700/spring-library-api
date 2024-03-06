package com.kalambo.libraryapi.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class IError {
    private Date timestamp;
    private String path;
    private String title;
    private String description;
    private List<IValidation> validations;

    public void addValidationError(String fieldName, String errorMessage) {
        if (Objects.isNull(validations)) {
            validations = new ArrayList<IValidation>();
        }

        validations.add(new IValidation(fieldName, errorMessage));
    }
}
