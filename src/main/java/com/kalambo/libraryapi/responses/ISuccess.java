package com.kalambo.libraryapi.responses;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ISuccess {
    String message;
    UUID resourceId;
}
