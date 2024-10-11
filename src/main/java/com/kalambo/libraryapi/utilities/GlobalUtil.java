package com.kalambo.libraryapi.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.dtos.AuditTrailDto;
import com.kalambo.libraryapi.responses.ISuccess;
import com.kalambo.libraryapi.services.AuditTrailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalUtil {
    @Autowired
    private AuditTrailService auditTrailService;

    public final ISuccess formatResponse(String entityName, String action, UUID resourceId) {
        String message;
        List<String> actions = Arrays.asList("create", "update", "delete");

        if (actions.contains(action))
            message = entityName + " " + action + "d successfully.";
        else
            message = action + " successfully.";

        return new ISuccess(message, resourceId.toString());
    }

    public void logRequest(String httpMethod, String endpoint) {
        log.info(formatLogEntry(httpMethod, endpoint));
    }

    public void logRequest(String httpMethod, String endpoint, String username) {
        log.info(formatLogEntry(httpMethod, endpoint) + ", username: " + username);
    }

    private String formatLogEntry(String httpMethod, String endpoint) {
        return httpMethod + " - /api/v1/" + endpoint;
    }

    public static String capitalizeFirstLetter(String word) {
        return StringUtils.capitalize(word.toLowerCase());
    }

    public void trackRequest(String action, String resourceName, UUID resourceId, String previousValues,
            String requestDto) {
        AuditTrailDto auditPayload = new AuditTrailDto()
                .setResourceId(resourceId).setAction(action).setResourceName(resourceName);

        if (List.of("create", "update").contains(action) && requestDto != null)
            auditPayload.setUpdatedValues(requestDto);

        if (List.of("delete", "update").contains(action) && previousValues != null)
            auditPayload.setPreviousValues(previousValues);

        auditTrailService.create(auditPayload);
    }
}
