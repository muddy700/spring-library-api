package com.kalambo.libraryapi.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.mappers.TaskMapper;
import com.kalambo.libraryapi.responses.IPage;

@Component
/**
 * A global utility class that handle some repetitive operations across different domains.
 *
 * @param <T1> It stands for the Entity mapped to the database.
 * @param <T2> It stands for the Dto of the same entity returned in a response.
 * @author Mohamed Mfaume Mohamed
 * @since March 09 2024
 */
public class GlobalUtil<T1, T2> {

    @Autowired
    // TODO: Create global mapper that links all domain mappers via T1
    TaskMapper taskMapper;

    public IPage<T2> formatResponse(Page<T1> entityPage) {
        List<T2> contents = new ArrayList<T2>(entityPage.getSize());

        // for (T1 entity : entityPage.getContent()) {
        //     contents.add(taskMapper.map(entity));
        // }

        IPage<T2> response = new IPage<T2>().setItems(contents)
                .setTotalPages(entityPage.getTotalPages()).setCurrentPage(entityPage.getNumber())
                .setTotalItems(entityPage.getTotalElements()).setCurrentSize(entityPage.getSize());

        return response;
    }
}
