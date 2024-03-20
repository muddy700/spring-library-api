package com.kalambo.libraryapi.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.responses.IPage;

@Component
/**
 * A global class that handles mapping of Spring Boot Page into custom
 * Page object for all domains.
 *
 * @param <T1> The Entity mapped to the database.
 * @param <T2> The Dto of the same entity returned in a response.
 * @author Mohamed Mfaume Mohamed
 * @since March 09 2024
 */
public class PageMapper<T1, T2> {
    @Autowired
    private GlobalMapper<T1, T2> globalMapper;

    public IPage<T2> paginate(Page<T1> entityPage) {
        List<T2> contents = new ArrayList<T2>(entityPage.getSize());
        entityPage.getContent().forEach(entity -> contents.add(globalMapper.map(entity)));

        IPage<T2> response = new IPage<T2>().setItems(contents)
                .setTotalPages(entityPage.getTotalPages()).setCurrentPage(entityPage.getNumber())
                .setTotalItems(entityPage.getTotalElements()).setCurrentSize(entityPage.getSize());

        return response;
    }

}
