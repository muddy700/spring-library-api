package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ActionDto {
    private String name;
    private List<String> resources;

    public void addResource(String resourceName) {
        if (Objects.isNull(resources)) {
            resources = new ArrayList<String>();
        }

        resources.add(resourceName);
    }
}
