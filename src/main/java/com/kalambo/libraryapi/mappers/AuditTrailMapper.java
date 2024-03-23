package com.kalambo.libraryapi.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalambo.libraryapi.entities.AuditTrail;
import com.kalambo.libraryapi.responses.IAuditTrail;

@Component
public class AuditTrailMapper {
    @Autowired
    private UserMapper userMapper;

    public IAuditTrail map(AuditTrail auditTrail) {
        IAuditTrail response = new IAuditTrail()
                .setUser(userMapper.map(auditTrail.getUser()))
                .setId(auditTrail.getId()).setCreatedAt(auditTrail.getCreatedAt())
                .setAction(auditTrail.getAction()).setResourceName(auditTrail.getResourceName())
                .setResourceId(auditTrail.getResourceId()).setPreviousValues(auditTrail.getPreviousValues())
                .setUpdatedValues(auditTrail.getUpdatedValues()).setDescription(auditTrail.getDescription());

        return response;
    }
}
