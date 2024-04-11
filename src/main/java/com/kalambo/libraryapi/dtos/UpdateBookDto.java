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

public class UpdateBookDto {
    private UUID id;
    private String title;
    private String description;

    private String content;
    private String coverImage;
    private String authorName;
    private Boolean enabled;

    @Override
    public String toString() {
        String result = "";

        for (Field field : Arrays.asList(UpdateBookDto.class.getDeclaredFields())) {
            try {
                if (field.getName() == "id" || field.get(this) == null)
                    return null;
                    
                result += result.length() > 0 ? ", " : "";
                result += field.getName() + "=" + field.get(this).toString();
            } catch (Exception ex) {
                // TODO: handle exception
            }
        }

        return result;
    }
}
