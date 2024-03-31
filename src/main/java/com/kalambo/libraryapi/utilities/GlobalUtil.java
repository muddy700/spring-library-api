package com.kalambo.libraryapi.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import com.kalambo.libraryapi.responses.ISuccess;

@Component
public class GlobalUtil {
    public static final ISuccess formatResponse(String entityName, String action, UUID resourceId) {
        String message;
        List<String> actions = Arrays.asList("create", "update", "delete");

        if (actions.contains(action))
            message = entityName + " " + action + "d successfully.";
        else
            message = action + " successfully.";

        return new ISuccess(message, resourceId);
    }
}
