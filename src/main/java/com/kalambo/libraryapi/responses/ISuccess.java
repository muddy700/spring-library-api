package com.kalambo.libraryapi.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ISuccess {
    String message;
    String resourceId;
}
