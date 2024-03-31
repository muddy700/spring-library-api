package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.entities.Book;
import com.kalambo.libraryapi.entities.BookReview;
import com.kalambo.libraryapi.entities.Permission;
import com.kalambo.libraryapi.entities.Role;
import com.kalambo.libraryapi.entities.Task;
import com.kalambo.libraryapi.entities.User;

@Component
/**
 * A global class that handles mapping of any Entity to its
 * corresponding response class.
 *
 * @param <T1> The Entity to be mapped.
 * @param <T2> The corresponding response Dto of the entity.
 * @author Mohamed Mfaume Mohamed
 * @since March 20 2024
 */
public class GlobalMapper<T1, T2> {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuditTrailMapper auditTrailMapper;

    @Autowired
    private BookReviewMapper bookReviewMapper;

    @SuppressWarnings("unchecked")
    public T2 map(T1 entity) {
        T2 result = null;

        if (entity.getClass().equals(Task.class))
            result = (T2) taskMapper.map((Task) entity);

        else if (entity.getClass().equals(Permission.class))
            result = (T2) permissionMapper.mapToV2((Permission) entity);

        else if (entity.getClass().equals(Role.class))
            result = (T2) roleMapper.mapToV2((Role) entity);

        else if (entity.getClass().equals(User.class))
            result = (T2) userMapper.mapToV2((User) entity);

        else if (entity.getClass().equals(Book.class))
            result = (T2) bookMapper.mapToV2((Book) entity);

        else if (entity.getClass().equals(AuditTrail.class))
            result = (T2) auditTrailMapper.mapToV2((AuditTrail) entity);

        else if (entity.getClass().equals(BookReview.class))
            result = (T2) bookReviewMapper.mapToV2((BookReview) entity);

        return result;
    }
}
