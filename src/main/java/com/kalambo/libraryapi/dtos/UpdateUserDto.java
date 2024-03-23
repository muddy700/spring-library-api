package com.kalambo.libraryapi.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
  private UUID id;

  @Size(min = 5, max = 40)
  @Email(message = "Enter a valid email")
  private String email;

  @Size(min = 12, max = 12)
  private String phoneNumber;

  @Size(min = 10, max = 50)
  private String fullName;

  @Size(min = 1, max = 1)
  private String gender;

  private UUID roleId;
  private Boolean enabled;
}
