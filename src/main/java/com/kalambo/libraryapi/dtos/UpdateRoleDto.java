package com.kalambo.libraryapi.dtos;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.Arrays;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class UpdateRoleDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean active;

    @Override
    public String toString() {
        String result = "";

        for (Field field : Arrays.asList(UpdateRoleDto.class.getDeclaredFields())) {
            try {
                if (field.getName() != "id" && field.get(this) != null) {
                    result += result.length() > 0 ? ", " : "";
                    result += field.getName() + "=" + field.get(this).toString();
                }
            } catch (Exception ex) {
                // TODO: handle exception
            }
        }

        return result;
    }
}
