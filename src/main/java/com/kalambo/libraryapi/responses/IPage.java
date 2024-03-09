
package com.kalambo.libraryapi.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IPage<T> {
    private Long totalItems;
    private Integer currentSize;
    private Integer totalPages;
    private Integer currentPage;
    private List<T> items;
}

