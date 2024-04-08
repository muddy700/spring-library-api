package com.kalambo.libraryapi.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ActionDto {
    private String name;
    private List<String> resources = new ArrayList<String>();
}
