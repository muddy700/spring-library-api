package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import com.kalambo.libraryapi.enums.GenderEnum;
import com.kalambo.libraryapi.exceptions.DtoManipulationException;

import java.util.Arrays;
import java.lang.reflect.Field;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class UpdateUserDto {
  private UUID id;

  @Size(min = 5, max = 40)
  @Email(message = "Enter a valid email")
  private String email;

  @Pattern(regexp = "^255(\\d{9})$", message = "Phone number must start with 255 and followed by 9 digits")
  private String phoneNumber;

  @Size(min = 10, max = 50)
  private String fullName;

  private GenderEnum gender;

  private UUID roleId;
  private Boolean enabled;

  @Override
  public String toString() {
    String result = "";

    for (Field field : Arrays.asList(UpdateUserDto.class.getDeclaredFields())) {
      try {
        if (field.getName() != "id" && field.get(this) != null) {
          result += result.length() > 0 ? ", " : "";
          result += field.getName() + "=" + field.get(this).toString();
        }
      } catch (Exception ex) {
        throw new DtoManipulationException("Failed to stringfy UpdateUserDto ==> " + ex.getMessage());
      }
    }

    return result;
  }
}
