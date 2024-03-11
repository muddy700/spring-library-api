package com.kalambo.libraryapi.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ISms {
    private String request_id;
    private Integer code;
    private String message;
    private Boolean successful;
    private Integer valid;
    private Integer invalid;
    private Integer duplicates;
}
