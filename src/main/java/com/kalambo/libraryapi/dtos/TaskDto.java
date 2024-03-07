package com.kalambo.libraryapi.dtos;

import com.kalambo.libraryapi.dtos.groups.OnCreate;
import com.kalambo.libraryapi.dtos.groups.OnUpdate;
import com.kalambo.libraryapi.entities.Task;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Null(groups = OnCreate.class, message = "Who told you to pass id..?")
    private Integer id;

    @NotBlank(groups = { OnCreate.class, OnUpdate.class }, message = "Task title is required")
    @Size(groups = { OnCreate.class, OnUpdate.class }, min = 5, max = 20)
    private String title;

    @NotNull(groups = { OnCreate.class, OnUpdate.class }, message = "Task duration is required")
    @Min(groups = { OnCreate.class, OnUpdate.class }, value = 2)
    @Max(groups = { OnCreate.class, OnUpdate.class }, value = 24)
    private Integer maxDuration;

    @NotBlank(groups = OnCreate.class, message = "Task author's name is required")
    @Null(groups = OnUpdate.class, message = "Name of the author is not updateable")
    private String authorName;

    @NotBlank(groups = OnCreate.class, message = "Task author's email is required")
    @Email(groups = OnCreate.class)
    @Null(groups = OnUpdate.class, message = "Email of the author is not updateable")
    private String authorEmail;

    @Null(groups = OnCreate.class, message = "Do not pass published when creating a task")
    @AssertTrue(groups = OnUpdate.class, message = "Published value must be true")
    private Boolean published;
 
    public Task toEntity() {
        return new Task().setTitle(title).setMaxDuration(maxDuration)
                .setAuthorName(authorName).setAuthorEmail(authorEmail);
    }
}
