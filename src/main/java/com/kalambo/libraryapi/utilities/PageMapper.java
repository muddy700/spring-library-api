package com.kalambo.libraryapi.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.entities.User;
import com.kalambo.libraryapi.mappers.PermissionMapper;
import com.kalambo.libraryapi.mappers.RoleMapper;
import com.kalambo.libraryapi.mappers.TaskMapper;
import com.kalambo.libraryapi.mappers.UserMapper;
import com.kalambo.libraryapi.responses.IPage;

@Component
/**
 * A global utility class that handles mapping of Spring Boot Page into custom
 * Page object for all domains.
 *
 * @param <T1> The Entity mapped to the database.
 * @param <T2> The Dto of the same entity returned in a response.
 * @author Mohamed Mfaume Mohamed
 * @since March 09 2024
 */
public class PageMapper<T1, T2> {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    public IPage<T2> paginate(Page<T1> entityPage) {
        List<T2> contents = new ArrayList<T2>(entityPage.getSize());
        entityPage.getContent().forEach(entity -> contents.add(map(entity)));

        IPage<T2> response = new IPage<T2>().setItems(contents)
                .setTotalPages(entityPage.getTotalPages()).setCurrentPage(entityPage.getNumber())
                .setTotalItems(entityPage.getTotalElements()).setCurrentSize(entityPage.getSize());

        return response;
    }

    @SuppressWarnings("unchecked")
    private T2 map(T1 entity) {
        T2 result = null;

        if (entity.getClass().equals(Task.class))
            result = (T2) taskMapper.map((Task) entity);

        else if (entity.getClass().equals(Permission.class))
            result = (T2) permissionMapper.map((Permission) entity);

        else if (entity.getClass().equals(Role.class))
            result = (T2) roleMapper.map((Role) entity);

        else if (entity.getClass().equals(User.class))
            result = (T2) userMapper.map((User) entity);

        return result;
    }
}
