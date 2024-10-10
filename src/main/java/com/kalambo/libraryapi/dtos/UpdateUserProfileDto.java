package com.kalambo.libraryapi.dtos;

import com.kalambo.libraryapi.exceptions.DtoManipulationException;

import java.util.Arrays;
import java.lang.reflect.Field;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class UpdateUserProfileDto {
    @Pattern(regexp = "^255(\\d{9})$", message = "Phone number must start with 255 and followed by 9 digits")
    private String phoneNumber;

    @Size(min = 10, max = 50)
    private String fullName;

    @Override
    public String toString() {
        String result = "";

        for (Field field : Arrays.asList(UpdateUserProfileDto.class.getDeclaredFields())) {
            try {
                if (field.getName() != "id" && field.get(this) != null) {
                    result += result.length() > 0 ? ", " : "";
                    result += field.getName() + "=" + field.get(this).toString();
                }
            } catch (Exception ex) {
                throw new DtoManipulationException("Failed to stringfy UpdateUserProfileDto ==> " + ex.getMessage());
            }
        }

        return result;
    }
}
